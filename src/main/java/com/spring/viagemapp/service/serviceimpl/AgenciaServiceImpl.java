package com.spring.viagemapp.service.serviceimpl;

import com.spring.viagemapp.model.Agencia;
import com.spring.viagemapp.repository.AgenciaRepository;
import com.spring.viagemapp.service.AgenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgenciaServiceImpl implements AgenciaService {

    @Autowired
    AgenciaRepository agenciaRepository;

    @Override
    public List<Agencia> findAll() {
        return agenciaRepository.findAll();
    }

    @Override
    public Optional<Agencia> findById(long id) {
        return agenciaRepository.findById(id);
    }

    @Override
    public Agencia save(Agencia agencia) {
        return agenciaRepository.save(agencia);
    }

    @Override
    public boolean existsByCnpj(String cnpj) {
        return agenciaRepository.existsByCnpj(cnpj);
    }

    @Override
    public boolean existsByEmail(String email) {
        return agenciaRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByNomeUsuario(String nomeUsuario) {
        return agenciaRepository.existsByNomeUsuario(nomeUsuario);
    }

    @Override
    public boolean existsByNome(String nome) {
        return agenciaRepository.existsByNome(nome);
    }

    @Override
    public Optional<Agencia> findByNomeUsuario(String nomeUsuario) {
        return agenciaRepository.findByNomeUsuario(nomeUsuario);
    }

    @Override
	public void deleteById(long id) {
		 agenciaRepository.deleteById(id);
	}
}
