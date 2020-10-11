package com.spring.viagemapp.repository;

import com.spring.viagemapp.model.Tag;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long>
{
	boolean existsById(long id);
    boolean existsByOpcao(String opcao);
    public Optional<Tag> findById(long id);
}
