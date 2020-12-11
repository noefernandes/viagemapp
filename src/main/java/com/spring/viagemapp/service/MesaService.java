package com.spring.viagemapp.service;

import com.spring.viagemapp.model.Mesa;
import com.spring.viagemapp.utils.MesaComNome;
import com.spring.viagemapp.utils.MesaTags;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

public interface MesaService extends ServicoService<Mesa>{
    //List<Viagem> findAll();
    List<MesaComNome> findAllSort(long idCliente);

    //Optional<Viagem> findById(long id);

    //void delete(Viagem viagem);

    //Viagem save(ViagemTags viagemTags);

    //boolean addNewTags(long id, ViagemTags viagemTags);

    //List<Viagem> findAllByAgencia(Agencia agencia);

    List<String> getTagsMesa(long idMesa);

    List<Mesa> getMesas(long id);

    void cadastrarMesa(MesaTags mesaTags, long id);

    void deletarMesa(long id);

}