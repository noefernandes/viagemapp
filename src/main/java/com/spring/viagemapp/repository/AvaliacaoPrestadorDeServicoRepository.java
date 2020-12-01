package com.spring.viagemapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.viagemapp.model.AvaliacaoPrestadorDeServico;

public interface AvaliacaoPrestadorDeServicoRepository<T extends AvaliacaoPrestadorDeServico> extends JpaRepository<T, Long> {

}
