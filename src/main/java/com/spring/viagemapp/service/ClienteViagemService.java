package com.spring.viagemapp.service;

import com.spring.viagemapp.model.ClienteViagem;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ClienteViagemService {
    List<ClienteViagem> findAll();
    ClienteViagem findById(long id);
    ClienteViagem save(ClienteViagem clienteViagem);
    void deleteById(long id);
    void deleteClienteViagem(long idCliente, long idViagem);
    public ClienteViagem comprarViagem(long idCliente, long idViagem);
}
