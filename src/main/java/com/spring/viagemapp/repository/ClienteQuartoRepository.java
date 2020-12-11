package com.spring.viagemapp.repository;

import com.spring.viagemapp.model.ClienteQuarto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ClienteQuartoRepository extends JpaRepository<ClienteQuarto, Long> {
    @Modifying
    @Query(value="delete from cliente_quarto cv where cv.id_cliente = ?1 and cv.id_quarto = ?2 ", nativeQuery = true)
    void deleteClienteViagem(long idCliente, long idQuarto);
}
