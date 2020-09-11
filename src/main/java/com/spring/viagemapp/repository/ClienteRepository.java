package com.spring.viagemapp.repository;

import com.spring.viagemapp.model.Agencia;
import com.spring.viagemapp.model.Cliente;
import com.spring.viagemapp.model.Viagem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    //Anotando o criando o método com este nome o spring implementa
    //o método por default
    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
    boolean existsByNomeUsuario(String nomeUsuario);
    boolean existsByNome(String nome);

    Optional<Cliente> findByNomeUsuario(String nomeUsuario);
}
