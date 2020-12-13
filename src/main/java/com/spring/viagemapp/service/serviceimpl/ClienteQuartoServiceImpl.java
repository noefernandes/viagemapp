package com.spring.viagemapp.service.serviceimpl;

import com.spring.viagemapp.error.CapacityException;
import com.spring.viagemapp.error.NotFoundClienteException;
import com.spring.viagemapp.error.NotFoundViagensException;
import com.spring.viagemapp.model.Cliente;
import com.spring.viagemapp.model.ClienteQuarto;
import com.spring.viagemapp.model.Quarto;
import com.spring.viagemapp.repository.ClienteRepository;
import com.spring.viagemapp.repository.ClienteQuartoRepository;
import com.spring.viagemapp.repository.QuartoRepository;
import com.spring.viagemapp.service.ClienteQuartoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ClienteQuartoServiceImpl implements ClienteQuartoService {
    @Autowired
    ClienteQuartoRepository clienteQuartoRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    QuartoRepository quartoRepository;

    @Override
    @Transactional(readOnly = false)
    public ClienteQuarto comprarQuarto(@PathVariable long idCliente, @PathVariable long idQuarto){
        int qtdClientes = clienteRepository.quantidadeDeClientes(idQuarto);
        Quarto quarto = quartoRepository.findById(idQuarto).get();
        
        if(quarto.getOcupado()) {
        	throw new CapacityException("Este quarto está ocupado");
        }


        ClienteQuarto clienteQuarto = new ClienteQuarto();
        clienteQuarto.setIdCliente(idCliente);
        clienteQuarto.setIdQuarto(idQuarto);
        quarto.setOcupado(true);
        quarto.setEstado("Ocupado");

        return clienteQuartoRepository.save(clienteQuarto);
    }

    @Override
    public List<ClienteQuarto> findAll() {
        return clienteQuartoRepository.findAll();
    }

    @Override
    public ClienteQuarto findById(long id) {
        return clienteQuartoRepository.findById(id).get();
    }

    @Override
    @Transactional(readOnly = false)
    public ClienteQuarto save(ClienteQuarto clienteQuarto) {
        return clienteQuartoRepository.save(clienteQuarto);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteById(long id) {
        clienteQuartoRepository.deleteById(id);
    }


    @Override
    @Transactional(readOnly = false)
    public void deleteClienteQuarto(long idCliente, long idQuarto){
        Optional<Cliente> clienteOp = clienteRepository.findById(idCliente);
        Optional<Quarto> quartoOp = quartoRepository.findById(idQuarto);

        if(!clienteOp.isPresent()){
            throw new NotFoundClienteException("Cliente não encontrado. Nenhum quarto foi deletado.");
        }

        if(!quartoOp.isPresent()){
            throw new NotFoundViagensException("Quarto não encontrado. Nenhum quarto foi deletado");
        }

        quartoOp.get().setOcupado(false);
        quartoOp.get().setEstado("Disponível");
        clienteQuartoRepository.deleteClienteQuarto(idCliente,idQuarto);
    }
}
