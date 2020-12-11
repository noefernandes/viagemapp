package com.spring.viagemapp.repository;

import com.spring.viagemapp.model.ClienteMesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ClienteMesaRepository extends JpaRepository<ClienteMesa, Long> {
    @Modifying
    @Query(value="delete from cliente_mesa cv where cv.id_cliente = ?1 and cv.id_mesa = ?2 ", nativeQuery = true)
    void deleteClienteMesa(long idCliente, long idMesa);
}
