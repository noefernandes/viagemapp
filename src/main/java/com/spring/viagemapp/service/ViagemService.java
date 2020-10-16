package com.spring.viagemapp.service;

import com.spring.viagemapp.model.Viagem;
import com.spring.viagemapp.utils.ViagemTags;

import java.util.List;
import java.util.Optional;

public interface ViagemService {
    List<Viagem> findAll();
    Optional<Viagem> findById(long id);
    void delete(Viagem viagem);
    Viagem save(ViagemTags viagemTags);
    boolean addNewTags(long id, ViagemTags viagemTags);
}
