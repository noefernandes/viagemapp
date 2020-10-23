package com.spring.viagemapp.service;

import com.spring.viagemapp.model.Agencia;
import com.spring.viagemapp.model.Avaliacoes;
import com.spring.viagemapp.model.Cliente;
import com.spring.viagemapp.model.Usuario;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface AgenciaService {
    List<Agencia> findAll();
    Agencia findById(long id);
    Agencia save(Agencia agencia);
    void deleteById(long id);
    boolean existsByCnpj(String cnpj);
    boolean existsByEmail(String email);
    boolean existsByNomeUsuario(String nomeUsuario);
    boolean existsByNome(String nome);
    Agencia addAvaliacao(Agencia agencia, Cliente cliente , Avaliacoes avaliacoes);
    List<Double> showNotas(Optional<Agencia> agencia);
    HashMap<String,String> showCometarios(Agencia agencia);

    Optional<Agencia> findByNomeUsuario(String nomeUsuario);
    Agencia checkLogin(Usuario usuario);
}
