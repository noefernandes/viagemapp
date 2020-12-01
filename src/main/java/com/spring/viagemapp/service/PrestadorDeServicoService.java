package com.spring.viagemapp.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.spring.viagemapp.model.Agencia;
import com.spring.viagemapp.model.AvaliacaoPerUser;
import com.spring.viagemapp.model.AvaliacaoPrestadorDeServico;
import com.spring.viagemapp.model.PrestadorDeServico;
import com.spring.viagemapp.model.Usuario;

public interface PrestadorDeServicoService<T extends PrestadorDeServico, S extends AvaliacaoPrestadorDeServico> 
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
    public T avaliarPrestador(@PathVariable long idCliente, @PathVariable long idPrestador,
    		@RequestBody @Valid S avaliacao) throws Exception;
}
