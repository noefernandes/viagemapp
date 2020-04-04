package com.spring.viagemapp.repository;

import com.spring.viagemapp.model.Viagem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViagemRepository extends JpaRepository<Viagem, Long> {
}
