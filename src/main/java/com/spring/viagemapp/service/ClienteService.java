package com.spring.viagemapp.service;


import com.spring.viagemapp.model.Cliente;

import java.util.List;

public interface ClienteService {
    List<Cliente> findAll();
    Cliente findById(long id);
    Cliente save(Cliente cliente);
    void deleteById(long id);
    boolean existsByCpf(String cpf);
}
