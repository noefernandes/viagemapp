package com.spring.viagemapp.service.serviceimpl;

import static com.spring.viagemapp.security.MD5.getMd5;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.spring.viagemapp.error.NotFoundAgenciaException;
import com.spring.viagemapp.error.NotFoundClienteException;
import com.spring.viagemapp.error.NotFoundLoginException;
import com.spring.viagemapp.error.RepeatedCpfException;
import com.spring.viagemapp.error.RepeatedEmailException;
import com.spring.viagemapp.error.RepeatedNameException;
import com.spring.viagemapp.error.RepeteadUsernameException;
import com.spring.viagemapp.error.WrongPasswordException;
import com.spring.viagemapp.model.Agencia;
import com.spring.viagemapp.model.AvaliacaoPerUser;
import com.spring.viagemapp.model.AvaliacaoPrestadorDeServico;
import com.spring.viagemapp.model.Cliente;
import com.spring.viagemapp.model.PrestadorDeServico;
import com.spring.viagemapp.model.Usuario;
import com.spring.viagemapp.repository.AvaliacaoPrestadorDeServicoRepository;
import com.spring.viagemapp.repository.ClienteRepository;
import com.spring.viagemapp.repository.PrestadorDeServicoRepository;
import com.spring.viagemapp.service.PrestadorDeServicoService;

@Service
@Transactional(readOnly = true)
public class PrestadorDeServicoServiceImpl<T extends PrestadorDeServico, S extends AvaliacaoPrestadorDeServico> implements PrestadorDeServicoService<T,S>{
	@Autowired
	PrestadorDeServicoRepository<T> prestadorRepository;
	@Autowired
	ClienteRepository clienteRepository;
	
	//@Override
    @Transactional(readOnly = false)
    public T avaliarPrestador(@PathVariable long idCliente,
                                            @PathVariable long idPrestador, @RequestBody @Valid S avaliacao) throws Exception{
        // Coleta a agência passada como parâmetro.
        Optional<T> prestador = prestadorRepository.findById(idPrestador);
        // Coleta o cliente passado como parâmetro
        Optional<Cliente> cliente = clienteRepository.findById(idCliente);

        if(!prestador.isPresent()){
            throw new NotFoundAgenciaException("Prestador com o ID " + idPrestador + " não encontrado ao avaliar!");
        }

        if(!cliente.isPresent()){
            throw new NotFoundClienteException("Cliente com ID " + idCliente + " não encontrado ao avaliar!");
        }

        //Agencia agencia = new Agencia(agenciaTemp.get());
        
        // Associamos os clientes e agência a avaliação e vice-versa

        if(!avaliacao.avaliarPrestador(prestador.get(), cliente.get())) 
        {
        	throw new Exception("Não foi possível realizar a avaliação");
        }

        return prestador.get();
        //return prestador.get();
        // Chamando o service de avaliação para salvar o dado
        
    }
	
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
