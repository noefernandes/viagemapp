package com.spring.viagemapp.service.serviceimpl;

import com.spring.viagemapp.error.RepeatedCpfException;
import com.spring.viagemapp.error.RepeatedEmailException;
import com.spring.viagemapp.error.RepeatedNameException;
import com.spring.viagemapp.error.RepeteadUsernameException;
import com.spring.viagemapp.model.Cliente;
import com.spring.viagemapp.repository.ClienteRepository;
import com.spring.viagemapp.service.ClienteService;
import com.spring.viagemapp.utils.ClienteTags;
import com.spring.viagemapp.utils.ViagemTags;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.spring.viagemapp.security.MD5.getMd5;

@Service
@Transactional(readOnly = true)
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
    @Transactional(readOnly = false)
    public Cliente save(ClienteTags clienteTags) {
        if(clienteRepository.existsByNome(clienteTags.cliente.getNome())){
            throw new RepeatedNameException("O nome já existe");
        }else if(clienteRepository.existsByCpf(clienteTags.cliente.getCpf())) {
            throw new RepeatedCpfException("O CNPJ já existe");
        }else if(clienteRepository.existsByEmail(clienteTags.cliente.getEmail())){
            throw new RepeatedEmailException("O E-mail já existe");
        }else if(clienteRepository.existsByNomeUsuario(clienteTags.cliente.getNomeUsuario())){
            throw new RepeteadUsernameException("O nome de usuário já existe");
        }

        List<String> tags = Arrays.asList(clienteTags.tagString.split(";"));
        clienteTags.cliente.setTags(tags);
        
        clienteTags.cliente.setSenha(getMd5(clienteTags.cliente.getSenha()));
        return clienteRepository.save(clienteTags.cliente);
    }

    @Override
    public boolean existsByCpf(String cpf) {
        return clienteRepository.existsByCpf(cpf);
    }

	@Override
    @Transactional(readOnly = false)
    public void deleteById(long id) { clienteRepository.deleteById(id); }

    @Override
    public boolean existsByEmail(String email){return clienteRepository.existsByEmail(email);}

    @Override
    public boolean existsByNome(String nome){ return  clienteRepository.existsByNome(nome);}

    @Override
    public boolean existsByNomeUsuario(String nomeUsuario){return clienteRepository.existsByNomeUsuario(nomeUsuario);}
    
    @Override
	public Optional<Cliente> findByNomeUsuario(String nomeUsuario) { return clienteRepository.findByNomeUsuario(nomeUsuario); }

    public boolean addNewTags(long id, ClienteTags clienteTags){
        if(findById(id).isPresent()) {
            List<String> tags = Arrays.asList(clienteTags.tagString.split(";"));
            clienteTags.cliente.addTags(tags);
            return true;
        }
        return false;

    }
    
}
