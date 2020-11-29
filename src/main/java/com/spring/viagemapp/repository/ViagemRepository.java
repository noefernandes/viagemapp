package com.spring.viagemapp.repository;

import com.spring.viagemapp.model.Viagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ViagemRepository extends ServicoRepository<Viagem> {
    //@Query(value="select cv.tags from viagem_tags cv where cv.viagem_idv = ?1", nativeQuery = true)
    //List<String> getTagsViagem(long idViagem);
}
