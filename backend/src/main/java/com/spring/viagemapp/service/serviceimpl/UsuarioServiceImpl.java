package com.spring.viagemapp.service.serviceimpl;

import com.spring.viagemapp.model.Usuario;
import com.spring.viagemapp.repository.UsuarioRepository;
import com.spring.viagemapp.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario findByNomeUsuario(String nomeUsuario) {
        return usuarioRepository.findByNomeUsuario(nomeUsuario);
    }

    @Override
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public void deleteByNomeUsuario(String nomeUsuario) {
        usuarioRepository.deleteByNomeUsuario(nomeUsuario);
    }

    @Override
    public boolean existsByNomeUsuario(String nomeUsuario) {
        return usuarioRepository.existsByNomeUsuario(nomeUsuario);
    }
}
