package com.spring.viagemapp.service.serviceimpl;

import com.spring.viagemapp.model.Cliente;
import com.spring.viagemapp.model.Viagem;
import com.spring.viagemapp.repository.ClienteRepository;
import com.spring.viagemapp.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    @Override
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    @Override
    public Optional<Cliente> findById(long id) {
        return clienteRepository.findById(id);
    }

    @Override
    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public boolean existsByCpf(String cpf) {
        return clienteRepository.existsByCpf(cpf);
    }

	@Override
    public void deleteById(long id) { clienteRepository.deleteById(id); }

    @Override
    public boolean existsByEmail(String email){return clienteRepository.existsByEmail(email);}

    @Override
    public boolean existsByNome(String nome){ return  clienteRepository.existsByNome(nome);}

    @Override
    public boolean existsByNomeUsuario(String nomeUsuario){return clienteRepository.existsByNomeUsuario(nomeUsuario);}
    
    @Override
	public Optional<Cliente> findByNomeUsuario(String nomeUsuario) { return clienteRepository.findByNomeUsuario(nomeUsuario); }

}
