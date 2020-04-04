package com.spring.viagemapp.repository;

import com.spring.viagemapp.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    //Anotando o criando o método com este nome o spring implementa
    //o método por default
    boolean existsByCpf(String cpf);
}
