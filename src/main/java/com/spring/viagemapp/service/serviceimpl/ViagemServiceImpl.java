package com.spring.viagemapp.service.serviceimpl;

import com.spring.viagemapp.model.Viagem;
import com.spring.viagemapp.repository.ViagemRepository;
import com.spring.viagemapp.service.ViagemService;
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

    @Override
    public Optional<Viagem> findById(long id) {
        return viagemRepository.findById(id);
    }

    @Override
    public Viagem save(ViagemTags viagemtags) {
        List<String> tags = Arrays.asList(viagemtags.tagString.split(";"));
        viagemtags.viagem.setTags(tags);
        return viagemRepository.save(viagemtags.viagem);
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
}
