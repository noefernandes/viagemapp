package com.spring.viagemapp.service;

import com.spring.viagemapp.model.Agencia;
import com.spring.viagemapp.model.AvaliacaoPerUser;
import com.spring.viagemapp.model.Cliente;
import com.spring.viagemapp.model.Usuario;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface AgenciaService {
    List<Agencia> findAll();
    Optional<Agencia> findById(long id);
    Agencia save(Agencia agencia);
    void deleteById(long id);
    boolean existsByCnpj(String cnpj);
    boolean existsByEmail(String email);
    boolean existsByNomeUsuario(String nomeUsuario);
    boolean existsByNome(String nome);
    Optional<Agencia> findByNomeUsuario(String nomeUsuario);
    Agencia checkLogin(Usuario usuario);
    void updateNota(Agencia agencia,  List<AvaliacaoPerUser> avaliacao);
}
