package com.spring.viagemapp.service.serviceimpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.viagemapp.error.NotFoundRestauranteException;
import com.spring.viagemapp.error.NotFoundMesasException;
import com.spring.viagemapp.model.Servico;
import com.spring.viagemapp.repository.ServicoRepository;
import com.spring.viagemapp.service.ServicoService;
import com.spring.viagemapp.utils.ServicoTags;

@Service
@Transactional(readOnly = false)
public class ServicoServiceImpl<T extends Servico> implements ServicoService<T>
{
	@Autowired
	ServicoRepository<T> servicoRepository;
	
	@Override
	public List<T> findAll() 
	{
		List<T> servicos = servicoRepository.findAll();
		if(servicos.isEmpty())
		{
			throw new NotFoundRestauranteException("Serviços não encontrados");
		}
		return servicos;
	}

	@Override
    public Optional<T> findById(long id) {
        Optional<T> servico;
        servico = servicoRepository.findById(id);
        if(!servico.isPresent()){
            throw new NotFoundMesasException("Serviço não encontrado");
        }
        return servico;
    }
	
	@Override
	public void delete(T servico) {
		servicoRepository.delete(servico);
	}

	@Override
    public Servico save(ServicoTags<T> servicoTags) {
        List<String> tags = Arrays.asList(servicoTags.tagString.split(";"));
        for(int i = 0; i < tags.size(); i++) {
            tags.set(i, StringUtils.stripAccents(tags.get(i)).trim().toLowerCase());
        }
        servicoTags.servico.setTags(tags);
        return servicoRepository.save(servicoTags.servico);
    }
	
	@Override
	public boolean addNewTags(long id, ServicoTags<T> servicoTags){
        if(findById(id).isPresent()) {
            List<String> tags = Arrays.asList(servicoTags.tagString.split(";"));

            servicoTags.servico.addTags(tags);
            return true;
        }
        return false;

    }
}
