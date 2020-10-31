package com.spring.viagemapp.service.serviceimpl;

import com.spring.viagemapp.model.Agencia;
import com.spring.viagemapp.model.ClienteViagem;
import com.spring.viagemapp.repository.ClienteRepository;
import com.spring.viagemapp.repository.ClienteViagemRepository;
import com.spring.viagemapp.repository.ViagemRepository;
import com.spring.viagemapp.service.AgenciaService;
import com.spring.viagemapp.service.ClienteViagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public void deleteViagem(long idCliente, long idViagem){
    
        ClienteViagem clienteViagem;

        clienteViagemRepository.deleteClienteViagem(idCliente,idViagem);
    }
}
