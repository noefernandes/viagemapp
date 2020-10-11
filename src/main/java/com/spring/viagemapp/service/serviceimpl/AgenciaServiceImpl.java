package com.spring.viagemapp.service.serviceimpl;

import com.spring.viagemapp.error.RepeatedCpfException;
import com.spring.viagemapp.error.RepeatedEmailException;
import com.spring.viagemapp.error.RepeatedNameException;
import com.spring.viagemapp.error.RepeteadUsernameException;
import com.spring.viagemapp.model.Agencia;
import com.spring.viagemapp.repository.AgenciaRepository;
import com.spring.viagemapp.service.AgenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.spring.viagemapp.security.MD5.getMd5;

@Service
@Transactional(readOnly = true)
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
    @Transactional(readOnly = false)
    public Agencia save(Agencia agencia) {
        if(agenciaRepository.existsByNome(agencia.getNome())){
            throw new RepeatedNameException("O nome já existe");
        }else if(agenciaRepository.existsByCnpj(agencia.getCnpj())) {
            throw new RepeatedCpfException("O CPF já existe");
        }else if(agenciaRepository.existsByEmail(agencia.getEmail())){
            throw new RepeatedEmailException("O E-mail já existe");
        }else if(agenciaRepository.existsByNomeUsuario(agencia.getNomeUsuario())){
            throw new RepeteadUsernameException("O nome de usuário já existe");
        }

        agencia.setSenha(getMd5(agencia.getSenha()));
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
    @Transactional(readOnly = false)
	public void deleteById(long id) {
		 agenciaRepository.deleteById(id);
	}
}
