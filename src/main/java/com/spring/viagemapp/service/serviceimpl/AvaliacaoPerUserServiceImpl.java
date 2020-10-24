package com.spring.viagemapp.service.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.viagemapp.error.NotFoundViagensException;
import com.spring.viagemapp.model.Agencia;
import com.spring.viagemapp.model.AvaliacaoPerUser;
import com.spring.viagemapp.model.Cliente;
import com.spring.viagemapp.repository.AvaliacaoPerUserRepository;
import com.spring.viagemapp.service.AvaliacaoPerUserService;

@Service
public class AvaliacaoPerUserServiceImpl implements AvaliacaoPerUserService {

	@Autowired
    AvaliacaoPerUserRepository AvaliacaoRepository;

	@Override
	public List<AvaliacaoPerUser> findAll() 
	{
		return AvaliacaoRepository.findAll();
	}

	@Override
	public List<AvaliacaoPerUser> findByAgencia(Agencia agencia) {
		if(agencia.getAvaliacoes().isEmpty()){
            throw new NotFoundViagensException("Esta agência não avaliações");
        }
        return agencia.getAvaliacoes();
	}

	@Override
	public List<AvaliacaoPerUser> findByCliente(Cliente cliente) {
		if(cliente.getAvaliacoes().isEmpty()){
            throw new NotFoundViagensException("Este cliente ainda não avaliou nenhuma agência");
        }
        return cliente.getAvaliacoes();
	}

	@Override
	public Optional<AvaliacaoPerUser> findById(long id){
		return AvaliacaoRepository.findById(id);
	}
	
	@Override
	public void delete(AvaliacaoPerUser avaliacao) 
	{
		AvaliacaoRepository.delete(avaliacao);
	}

	@Override
	public AvaliacaoPerUser save(AvaliacaoPerUser avaliacao) 
	{
		return AvaliacaoRepository.save(avaliacao);
	}
	
	
	
}
