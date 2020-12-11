package com.spring.viagemapp.service;

import com.spring.viagemapp.model.Restaurante;
import com.spring.viagemapp.model.AvaliacaoPerUser;
import com.spring.viagemapp.model.Usuario;
import com.spring.viagemapp.utils.ComentarioComNome;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface RestauranteService extends PrestadorDeServicoService<Restaurante, AvaliacaoPerUser>{
    //List<Agencia> findAll();
    //Optional<Agencia> findById(long id);
    //Agencia save(Agencia agencia);
    //void deleteById(long id);
    //boolean existsByCnpj(String cnpj);
    //boolean existsByEmail(String email);
    //boolean existsByNomeUsuario(String nomeUsuario);
    //boolean existsByNome(String nome);
    //Optional<Agencia> findByNomeUsuario(String nomeUsuario);
    //Agencia checkLogin(Usuario usuario);
    void updateNota(Restaurante restaurante,  List<AvaliacaoPerUser> avaliacao);
    List<Double> showNotas(Restaurante restaurante);
    List<ComentarioComNome> showComentarios(Restaurante restaurante);
    public void salvarAvaliacao(AvaliacaoPerUser avaliacao);
    //public Agencia avaliarAgencia(@PathVariable long idCliente,
    //                              @PathVariable long idAgencia, @RequestBody @Valid AvaliacaoPerUser avaliacao);
}
