package com.spring.viagemapp.service;

import java.util.List;
import java.util.Optional;

import com.spring.viagemapp.model.Servico;
import com.spring.viagemapp.utils.ServicoTags;

public interface ServicoService <T extends Servico> 
{
	List<T> findAll();
	Optional<T> findById(long id);
	void delete(T servico);
	Servico save(ServicoTags<T> servicoTags);
	boolean addNewTags(long id, ServicoTags<T> servicoTags);
    //List<T> findAllByPrestador(PrestadorDeServico prestador);
}
