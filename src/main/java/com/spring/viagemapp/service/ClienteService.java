package com.spring.viagemapp.service;


import com.spring.viagemapp.model.Cliente;
import com.spring.viagemapp.model.Usuario;
import com.spring.viagemapp.utils.ClienteTags;

import java.util.List;
import java.util.Optional;

public interface ClienteService {
    List<Cliente> findAll();
    Optional<Cliente> findById(long id);
    Cliente save(ClienteTags clienteTags);
    void deleteById(long id);
    boolean existsByCpf(String cpf);
    boolean existsByNomeUsuario(String nomeUsuario);
    boolean existsByEmail(String email);
    boolean existsByNome(String nome);
    boolean addNewTags(long id, ClienteTags clienteTags);

    Optional<Cliente> findByNomeUsuario(String nomeUsuario);
    Cliente checkLogin(Usuario usuario);


    }
