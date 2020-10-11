package com.spring.viagemapp.service;

import java.util.List;
import java.util.Optional;

import com.spring.viagemapp.model.Tag;


public interface TagService 
{
	 	List<Tag> findAll();
	    Optional<Tag> findById(long id);
	    Tag save(Tag tag);
	    void deleteById(long id);
	    boolean existsByOpcao(String opcao);
}
