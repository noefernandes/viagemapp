package com.spring.viagemapp.repository;

import com.spring.viagemapp.model.Restaurante;
import com.spring.viagemapp.model.Cliente;
import com.spring.viagemapp.model.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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

    @Query(value="select v.* from cliente_mesa cv, servico v where cv.id_cliente = ?1" +
            " and cv.id_mesa = v.id", nativeQuery = true)
    List<Object[]> getMesasDoCliente(long idCliente);

    @Query(value="select ct.tags from cliente_tags ct where ct.cliente_id = ?1", nativeQuery = true)
    List<String> getTagsCliente(long idCliente);

    @Query(value = "select count(*) from cliente_mesa cv, cliente c where" +
            " cv.id_mesa = ?1 and cv.id_cliente = c.id", nativeQuery = true)
    int quantidadeDeClientes(long idMesa);
}

