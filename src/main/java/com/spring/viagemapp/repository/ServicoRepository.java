package com.spring.viagemapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.viagemapp.model.Servico;

public interface ServicoRepository<T extends Servico> extends JpaRepository<T, Long>
{
	@Query(value="select cv.tags from servico_tags cv where cv.servico_id = ?1", nativeQuery = true)
    List<String> getTagsServico(long idServico);
}
