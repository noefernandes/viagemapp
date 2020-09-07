package com.spring.viagemapp.repository;

import com.spring.viagemapp.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByNomeUsuario(String nomeUsuario);
    boolean existsByNomeUsuario(String nomeUsuario);
    void deleteByNomeUsuario(String nomeUsuario);
}
