package com.spring.viagemapp.repository;

import com.spring.viagemapp.model.Quarto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuartoRepository extends ServicoRepository<Quarto> {
    //@Query(value="select cv.tags from viagem_tags cv where cv.viagem_idv = ?1", nativeQuery = true)
    //List<String> getTagsViagem(long idViagem);
}
