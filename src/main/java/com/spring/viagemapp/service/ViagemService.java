package com.spring.viagemapp.service;

import com.spring.viagemapp.model.Agencia;
import com.spring.viagemapp.model.Viagem;

import java.util.List;

public interface ViagemService {
    List<Viagem> findAll();
    Viagem findById(long id);
    void deleteById(long id);
    Viagem save(Viagem viagem);
}
