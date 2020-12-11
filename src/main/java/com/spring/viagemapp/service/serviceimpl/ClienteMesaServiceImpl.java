package com.spring.viagemapp.service.serviceimpl;

import com.spring.viagemapp.error.CapacityException;
import com.spring.viagemapp.error.NotFoundClienteException;
import com.spring.viagemapp.error.NotFoundMesasException;
import com.spring.viagemapp.model.Cliente;
import com.spring.viagemapp.model.ClienteMesa;
import com.spring.viagemapp.model.Mesa;
import com.spring.viagemapp.repository.ClienteMesaRepository;
import com.spring.viagemapp.repository.ClienteRepository;
import com.spring.viagemapp.repository.MesaRepository;
import com.spring.viagemapp.service.ClienteMesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ClienteMesaServiceImpl implements ClienteMesaService {
    @Autowired
    ClienteMesaRepository clienteMesaRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    MesaRepository mesaRepository;

    @Override
    @Transactional(readOnly = false)
    public ClienteMesa comprarMesa(@PathVariable long idCliente, @PathVariable long idMesa){
        int qtdClientes = clienteRepository.quantidadeDeClientes(idMesa);
        Mesa mesa = mesaRepository.findById(idMesa).get();

        if(qtdClientes >= mesa.getCapacidade()) {
            throw new CapacityException("A mesa já está cheia!");
        }

        ClienteMesa clienteMesa = new ClienteMesa();
        clienteMesa.setIdCliente(idCliente);
        clienteMesa.setIdMesa(idMesa);

        mesa.setQtdPassageiros(qtdClientes + 1);

        return clienteMesaRepository.save(clienteMesa);
    }

    @Override
    public List<ClienteMesa> findAll() {
        return clienteMesaRepository.findAll();
    }

    @Override
    public ClienteMesa findById(long id) {
        return clienteMesaRepository.findById(id).get();
    }

    @Override
    @Transactional(readOnly = false)
    public ClienteMesa save(ClienteMesa clienteMesa) {
        return clienteMesaRepository.save(clienteMesa);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteById(long id) {
        clienteMesaRepository.deleteById(id);
    }


    @Override
    @Transactional(readOnly = false)
    public void deleteClienteMesa(long idCliente, long idMesa){
        Optional<Cliente> clienteOp = clienteRepository.findById(idCliente);
        Optional<Mesa> mesaOp = mesaRepository.findById(idMesa);

        if(!clienteOp.isPresent()){
            throw new NotFoundClienteException("Cliente não encontrado. Nenhuma viagem foi deletada.");
        }

        if(!mesaOp.isPresent()){
            throw new NotFoundMesasException("Viagem não encontrada. Nenhuma viagem foi deletada");
        }

        int qtdClientes = clienteRepository.quantidadeDeClientes(idMesa);
        mesaOp.get().setQtdPassageiros(qtdClientes - 1);

        clienteMesaRepository.deleteClienteMesa(idCliente, idMesa);
    }
}
