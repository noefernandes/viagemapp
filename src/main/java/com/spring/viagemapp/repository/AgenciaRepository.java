package com.spring.viagemapp.repository;

import com.spring.viagemapp.model.Agencia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgenciaRepository extends JpaRepository<Agencia, Long> {
    //Anotando o criando o método com este nome o spring implementa
    //o método por default
    boolean existsByCnpj(String cpf);
}
