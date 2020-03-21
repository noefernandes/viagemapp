package com.spring.viagemapp.service;

import com.spring.viagemapp.model.Agencia;

import java.util.List;

public interface AgenciaService {
    List<Agencia> findAll();
    Agencia findById(long id);
    Agencia save(Agencia agencia);
    void deleteById(long id);
    boolean existsByCnpj(String cnpj);
}
