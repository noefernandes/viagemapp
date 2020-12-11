package com.spring.viagemapp.service;

import com.spring.viagemapp.model.ClienteMesa;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ClienteMesaService {
    List<ClienteMesa> findAll();
    ClienteMesa findById(long id);
    ClienteMesa save(ClienteMesa clienteMesa);
    void deleteById(long id);
    void deleteClienteMesa(long idCliente, long idMesa);
    public ClienteMesa comprarMesa(long idCliente, long idMesa);
}
