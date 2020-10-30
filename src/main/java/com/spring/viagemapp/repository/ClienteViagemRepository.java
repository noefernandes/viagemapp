package com.spring.viagemapp.repository;

import com.spring.viagemapp.model.ClienteViagem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteViagemRepository extends JpaRepository<ClienteViagem, Long> {

    @Query(value="delete from clienteViagem cv where cv.idCliente = ?1 
    and cv.idViagem = ?2 ", nativeQuery = true)
    void deleteClienteViagem(long idCliente, long idViagem);
}
