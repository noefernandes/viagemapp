package com.spring.viagemapp.service;

import com.spring.viagemapp.model.Usuario;
import java.util.List;

public interface UsuarioService {
    List<Usuario> findAll();
    Usuario findByNomeUsuario(String nomeUsuario);
    Usuario save(Usuario usuario);
    void deleteByNomeUsuario(String nomeUsuario);
    boolean existsByNomeUsuario(String nomeUsuario);
}
