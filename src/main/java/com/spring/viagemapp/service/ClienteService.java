package com.spring.viagemapp.service;


import com.spring.viagemapp.model.Agencia;
import com.spring.viagemapp.model.Cliente;
import com.spring.viagemapp.model.Viagem;

import java.util.List;
import java.util.Optional;

public interface ClienteService {
    List<Cliente> findAll();
    Optional<Cliente> findById(long id);
    Cliente save(Cliente cliente);
    void deleteById(long id);
    boolean existsByCpf(String cpf);
    boolean existsByNomeUsuario(String nomeUsuario);
    boolean existsByEmail(String email);
    boolean existsByNome(String nome);

    Optional<Cliente> findByNomeUsuario(String nomeUsuario);

}
