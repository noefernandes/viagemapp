package com.spring.viagemapp.service;

import com.spring.viagemapp.model.ClienteQuarto;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ClienteViagemService {
    List<ClienteQuarto> findAll();
    ClienteQuarto findById(long id);
    ClienteQuarto save(ClienteQuarto clienteQuarto);
    void deleteById(long id);
    void deleteClienteViagem(long idCliente, long idQuarto);
    public ClienteQuarto comprarViagem(long idCliente, long idQuarto);
}
