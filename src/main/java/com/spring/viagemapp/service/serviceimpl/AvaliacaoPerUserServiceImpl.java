package com.spring.viagemapp.service.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.viagemapp.error.NotFoundHotelException;
import com.spring.viagemapp.error.NotFoundViagensException;
import com.spring.viagemapp.model.Hotel;
import com.spring.viagemapp.model.AvaliacaoPerUser;
import com.spring.viagemapp.model.Cliente;
import com.spring.viagemapp.repository.AvaliacaoPerUserRepository;
import com.spring.viagemapp.service.AvaliacaoPerUserService;

@Service
@Transactional(readOnly = false)
public class AvaliacaoPerUserServiceImpl implements AvaliacaoPerUserService {

	@Autowired
    AvaliacaoPerUserRepository AvaliacaoRepository;

	@Override
	public List<AvaliacaoPerUser> findAll() 
	{
		return AvaliacaoRepository.findAll();
	}

	// TODO: Atualizar a excessão
	@Override
	public List<AvaliacaoPerUser> findByHotel(Hotel hotel) {
		if(hotel.getAvaliacoes().isEmpty()){
            throw new NotFoundHotelException("Esta agência não tem avaliações");
        }
        return hotel.getAvaliacoes();
	}

	// TODO: Atualizar a excessão
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
