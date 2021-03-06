package com.spring.viagemapp.repository;

import com.spring.viagemapp.model.Hotel;
import com.spring.viagemapp.model.PrestadorDeServico;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface HotelRepository extends PrestadorDeServicoRepository<Hotel> {
    //Anotando o criando o método com este nome o spring implementa
    //o método por default
    //boolean existsByCnpj(String cnpj);
    //boolean existsByEmail(String email);
    //boolean existsByNomeUsuario(String nomeUsuario);
    //boolean existsByNome(String nome);

    //Optional<PrestadorDeServico> findByNomeUsuario(String nomeUsuario);
}
