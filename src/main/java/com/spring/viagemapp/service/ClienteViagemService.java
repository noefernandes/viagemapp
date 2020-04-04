package com.spring.viagemapp.service;

import com.spring.viagemapp.model.Agencia;
import com.spring.viagemapp.model.ClienteViagem;

import java.util.List;

public interface ClienteViagemService {
    List<ClienteViagem> findAll();
    ClienteViagem findById(long id);
    ClienteViagem save(ClienteViagem clienteViagem);
    void deleteById(long id);
}
