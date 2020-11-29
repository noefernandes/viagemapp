package com.spring.viagemapp.service.serviceimpl;

import static com.spring.viagemapp.security.MD5.getMd5;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.viagemapp.error.NotFoundAgenciaException;
import com.spring.viagemapp.error.NotFoundLoginException;
import com.spring.viagemapp.error.RepeatedCpfException;
import com.spring.viagemapp.error.RepeatedEmailException;
import com.spring.viagemapp.error.RepeatedNameException;
import com.spring.viagemapp.error.RepeteadUsernameException;
import com.spring.viagemapp.error.WrongPasswordException;
import com.spring.viagemapp.model.PrestadorDeServico;
import com.spring.viagemapp.model.Usuario;
import com.spring.viagemapp.repository.PrestadorDeServicoRepository;
import com.spring.viagemapp.service.PrestadorDeServicoService;

@Service
@Transactional(readOnly = true)
public class PrestadorDeServicoServiceImpl<T extends PrestadorDeServico> implements PrestadorDeServicoService<T>{
	@Autowired
	PrestadorDeServicoRepository<T> prestadorRepository;
	
	@Override
    public List<T> findAll() {
        List<T> prestadores = prestadorRepository.findAll();
        if(prestadores.isEmpty()){
            throw new NotFoundAgenciaException("Prestadores de serviço não encontrados");
        }
        
        return prestadores;
    }

	@Override
    public Optional<T> findById(long id) {
        Optional<T> prestador = prestadorRepository.findById(id);
        if(!prestador.isPresent()){
            throw new NotFoundAgenciaException("Prestador de serviço não encontrado");
        }

        
        return prestador;
    }

	@Override
    @Transactional(readOnly = false)
    public T save(T prestador) {
        if(prestadorRepository.existsByNome(prestador.getNome())){
            throw new RepeatedNameException("O nome já existe");
        }else if(prestadorRepository.existsByCnpj(prestador.getCnpj())) {
            throw new RepeatedCpfException("O CPF já existe");
        }else if(prestadorRepository.existsByEmail(prestador.getEmail())){
            throw new RepeatedEmailException("O E-mail já existe");
        }else if(prestadorRepository.existsByNomeUsuario(prestador.getNomeUsuario())){
            throw new RepeteadUsernameException("O nome de usuário já existe");
        }

        prestador.setSenha(getMd5(prestador.getSenha()));
        return prestadorRepository.save(prestador);
    }

	@Override
    @Transactional(readOnly = false)
	public void deleteById(long id) {
    	prestadorRepository.deleteById(id);
	}

	@Override
	public boolean existsByCnpj(String cnpj) {
		return prestadorRepository.existsByCnpj(cnpj);
	}

	@Override
	public boolean existsByEmail(String email) {
		return prestadorRepository.existsByEmail(email);
	}

	@Override
	public boolean existsByNomeUsuario(String nomeUsuario) {
		return prestadorRepository.existsByNomeUsuario(nomeUsuario);
	}

	@Override
	public boolean existsByNome(String nome) {
		return prestadorRepository.existsByNome(nome);
	}

	@Override
	public Optional<T> findByNomeUsuario(String nomeUsuario) {
		Optional<T> prestador = prestadorRepository.findByNomeUsuario(nomeUsuario);
		return prestador;
	}

	@Override
    public T checkLogin(Usuario usuario) {
        Optional<T> prestadorOp = prestadorRepository.findByNomeUsuario(usuario.getNomeUsuario());

        if (!prestadorOp.isPresent()) {
            throw  new NotFoundLoginException("O usuário não existe");
        } else if (!prestadorOp.get().getSenha().equals(getMd5(usuario.getSenha()))) {
            throw new WrongPasswordException("Senha incorreta");
        }
        return prestadorOp.get();
    }

}
