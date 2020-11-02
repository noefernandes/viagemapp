package com.spring.viagemapp.service.serviceimpl;

import com.spring.viagemapp.error.CapacityException;
import com.spring.viagemapp.error.NotFoundClienteException;
import com.spring.viagemapp.error.NotFoundViagensException;
import com.spring.viagemapp.model.Cliente;
import com.spring.viagemapp.model.ClienteViagem;
import com.spring.viagemapp.model.Viagem;
import com.spring.viagemapp.repository.ClienteRepository;
import com.spring.viagemapp.repository.ClienteViagemRepository;
import com.spring.viagemapp.repository.ViagemRepository;
import com.spring.viagemapp.service.ClienteViagemService;
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
public class ClienteViagemServiceImpl implements ClienteViagemService {
    @Autowired
    ClienteViagemRepository clienteViagemRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    ViagemRepository viagemRepository;

    @Override
    @Transactional(readOnly = false)
    public ClienteViagem comprarViagem(@PathVariable long idCliente, @PathVariable long idViagem){
        int qtdClientes = clienteRepository.quantidadeDeClientes(idViagem);
        Viagem viagem = viagemRepository.findById(idViagem).get();

        if(qtdClientes >= viagem.getCapacidade()) {
            throw new CapacityException("A viagem já está cheia!");
        }

        ClienteViagem clienteViagem = new ClienteViagem();
        clienteViagem.setIdCliente(idCliente);
        clienteViagem.setIdViagem(idViagem);

        viagem.setQtdPassageiros(qtdClientes + 1);

        return clienteViagemRepository.save(clienteViagem);
    }

    @Override
    public List<ClienteViagem> findAll() {
        return clienteViagemRepository.findAll();
    }

    @Override
    public ClienteViagem findById(long id) {
        return clienteViagemRepository.findById(id).get();
    }

    @Override
    @Transactional(readOnly = false)
    public ClienteViagem save(ClienteViagem clienteViagem) {
        return clienteViagemRepository.save(clienteViagem);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteById(long id) {
        clienteViagemRepository.deleteById(id);
    }


    @Override
    @Transactional(readOnly = false)
    public void deleteClienteViagem(long idCliente, long idViagem){
        Optional<Cliente> clienteOp = clienteRepository.findById(idCliente);
        Optional<Viagem> viagemOp = viagemRepository.findById(idViagem);

        if(!clienteOp.isPresent()){
            throw new NotFoundClienteException("Cliente não encontrado. Nenhuma viagem foi deletada.");
        }

        if(!viagemOp.isPresent()){
            throw new NotFoundViagensException("Viagem não encontrada. Nenhuma viagem foi deletada");
        }

        int qtdClientes = clienteRepository.quantidadeDeClientes(idViagem);
        viagemOp.get().setQtdPassageiros(qtdClientes - 1);

        clienteViagemRepository.deleteClienteViagem(idCliente,idViagem);
    }
}
