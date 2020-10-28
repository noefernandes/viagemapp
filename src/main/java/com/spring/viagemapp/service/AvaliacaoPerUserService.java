package com.spring.viagemapp.service;

import java.util.List;
import java.util.Optional;

import com.spring.viagemapp.model.Agencia;
import com.spring.viagemapp.model.AvaliacaoPerUser;
import com.spring.viagemapp.model.Cliente;


public interface AvaliacaoPerUserService 
{
	List<AvaliacaoPerUser> findAll();

	Optional<AvaliacaoPerUser> findById(long id);
	
    List<AvaliacaoPerUser> findByAgencia(Agencia agencia);
    
    List<AvaliacaoPerUser> findByCliente(Cliente cliente);

    void delete(AvaliacaoPerUser avaliacao);

    AvaliacaoPerUser save(AvaliacaoPerUser avaliacao);
}
