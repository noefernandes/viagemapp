package com.spring.viagemapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

import com.spring.viagemapp.model.PrestadorDeServico;
import com.spring.viagemapp.model.Usuario;

public interface PrestadorDeServicoService<T extends PrestadorDeServico> 
{
	List<T> findAll();
    Optional<T> findById(long id);
	T save(T prestador);
    void deleteById(long id);
    boolean existsByCnpj(String cnpj);
    boolean existsByEmail(String email);
    boolean existsByNomeUsuario(String nomeUsuario);
    boolean existsByNome(String nome);
    Optional<T> findByNomeUsuario(String nomeUsuario);
    T checkLogin(Usuario usuario);
}
