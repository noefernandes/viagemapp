package com.spring.viagemapp.repository;

import com.spring.viagemapp.model.ClienteViagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ClienteViagemRepository extends JpaRepository<ClienteViagem, Long> {
    @Modifying
    @Query(value="delete from cliente_viagem cv where cv.id_cliente = ?1 and cv.id_viagem = ?2 ", nativeQuery = true)
    void deleteClienteViagem(long idCliente, long idViagem);
}
