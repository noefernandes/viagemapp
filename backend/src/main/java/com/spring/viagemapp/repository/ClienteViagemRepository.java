package com.spring.viagemapp.repository;

import com.spring.viagemapp.model.ClienteViagem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteViagemRepository extends JpaRepository<ClienteViagem, Long> {
}
