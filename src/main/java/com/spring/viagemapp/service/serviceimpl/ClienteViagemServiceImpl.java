package com.spring.viagemapp.service.serviceimpl;

import com.spring.viagemapp.model.Agencia;
import com.spring.viagemapp.model.ClienteViagem;
import com.spring.viagemapp.repository.ClienteViagemRepository;
import com.spring.viagemapp.service.AgenciaService;
import com.spring.viagemapp.service.ClienteViagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteViagemServiceImpl implements ClienteViagemService {
    @Autowired
    ClienteViagemRepository clienteViagemRepository;


    @Override
    public List<ClienteViagem> findAll() {
        return clienteViagemRepository.findAll();
    }

    @Override
    public ClienteViagem findById(long id) {
        return clienteViagemRepository.findById(id).get();
    }

    @Override
    public ClienteViagem save(ClienteViagem clienteViagem) {
        return clienteViagemRepository.save(clienteViagem);
    }

    @Override
    public void deleteById(long id) {
        clienteViagemRepository.deleteById(id);
    }
}
