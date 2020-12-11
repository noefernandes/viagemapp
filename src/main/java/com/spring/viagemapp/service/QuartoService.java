package com.spring.viagemapp.service;

import com.spring.viagemapp.model.Hotel;
import com.spring.viagemapp.model.Quarto;
import com.spring.viagemapp.utils.QuartoComNome;
import com.spring.viagemapp.utils.QuartoTags;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

public interface QuartoService extends ServicoService<Quarto>{
    //List<Viagem> findAll();
    List<QuartoComNome> findAllSort(long idCliente);

    //Optional<Viagem> findById(long id);

    //void delete(Viagem viagem);

    //Viagem save(ViagemTags viagemTags);

    //boolean addNewTags(long id, ViagemTags viagemTags);

    //List<Viagem> findAllByAgencia(Agencia agencia);

    List<String> getTagsQuarto(long idQuarto);

    List<Quarto> getQuartos(long id);

    void cadastrarQuarto(QuartoTags quartoTags, long id);

    void deletarQuarto(long id);

}