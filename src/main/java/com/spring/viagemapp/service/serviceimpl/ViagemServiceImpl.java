package com.spring.viagemapp.service.serviceimpl;
import com.spring.viagemapp.error.*;
import com.spring.viagemapp.model.Agencia;
import com.spring.viagemapp.model.Viagem;
import com.spring.viagemapp.repository.ViagemRepository;
import com.spring.viagemapp.service.ViagemService;
import com.spring.viagemapp.utils.ViagemTags;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ViagemServiceImpl implements ViagemService {

    @Autowired
    ViagemRepository viagemRepository;

    @Override
    public List<Viagem> findAll() {
        return viagemRepository.findAll();
    }
    public List<Viagem> findAllByAgencia(Agencia agencia){
        if(agencia.getViagens().isEmpty()){
            throw new NotFoundViagensException("Esta agência não tem viagens");
        }
        return agencia.getViagens();
    }

    @Override
    public Optional<Viagem> findById(long id) {
        return viagemRepository.findById(id);
    }

    public Viagem save(ViagemTags viagemTags) {
        List<String> tags = Arrays.asList(viagemTags.tagString.split(";"));
        for(int i = 0; i < tags.size(); i++) {
            System.out.println("Valor1:" + tags.get(i));
            tags.set(i, StringUtils.stripAccents(tags.get(i)).trim().toLowerCase());
            System.out.println("Valor2:" + tags.get(i));
        }
        viagemTags.viagem.setTags(tags);
        //System.out.println("local partida: " + viagemTags.viagem.getLocalPartida());
        return viagemRepository.save(viagemTags.viagem);
    }

	@Override
	public void delete(Viagem viagem) {
		viagemRepository.delete(viagem);
	}

	public boolean addNewTags(long id, ViagemTags viagemtags){
        if(findById(id).isPresent()) {
            List<String> tags = Arrays.asList(viagemtags.tagString.split(";"));

            viagemtags.viagem.addTags(tags);
            return true;
        }
        return false;

    }

    public List<String> getTagsViagem(long idViagem){
        return viagemRepository.getTagsViagem(idViagem);
    }
}
