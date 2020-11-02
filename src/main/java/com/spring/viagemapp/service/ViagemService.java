package com.spring.viagemapp.service;

import com.spring.viagemapp.model.Agencia;
import com.spring.viagemapp.model.Viagem;
import com.spring.viagemapp.utils.ViagemComNome;
import com.spring.viagemapp.utils.ViagemTags;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

public interface ViagemService {
    List<Viagem> findAll();
    List<ViagemComNome> findAllSort(long idCliente);

    Optional<Viagem> findById(long id);

    void delete(Viagem viagem);

    Viagem save(ViagemTags viagemTags);

    boolean addNewTags(long id, ViagemTags viagemTags);

    List<Viagem> findAllByAgencia(Agencia agencia);

    List<String> getTagsViagem(long idViagem);

    List<Viagem> getViagens(long id);

    void cadastrarViagem(ViagemTags viagemTags, long id);

    void deletarViagem(long id);

}