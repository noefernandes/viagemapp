package com.spring.viagemapp.service.serviceimpl;

import com.spring.viagemapp.error.*;
import com.spring.viagemapp.model.Restaurante;
import com.spring.viagemapp.model.AvaliacaoPerUser;
import com.spring.viagemapp.model.Cliente;
import com.spring.viagemapp.model.PrestadorDeServico;
import com.spring.viagemapp.model.Restaurante;
import com.spring.viagemapp.model.Usuario;
import com.spring.viagemapp.repository.RestauranteRepository;
import com.spring.viagemapp.repository.AvaliacaoPerUserRepository;
import com.spring.viagemapp.repository.ClienteRepository;
import com.spring.viagemapp.repository.RestauranteRepository;
import com.spring.viagemapp.service.RestauranteService;
import com.spring.viagemapp.utils.ComentarioComNome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.spring.viagemapp.security.MD5.getMd5;

@Service
@Transactional(readOnly = false)
public class RestauranteServiceImpl extends PrestadorDeServicoServiceImpl<Restaurante, AvaliacaoPerUser> implements RestauranteService{

    @Autowired
    RestauranteRepository restauranteRepository;
	
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    AvaliacaoPerUserRepository avaliacaoPerUserRepository;

    //@Override
    //public List<Agencia> findAll() {
    //    List<Agencia> agenciasTemp = agenciaRepository.findAll();
    //    if(agenciasTemp.isEmpty()){
    //        throw new NotFoundAgenciaException("Agências não encontradas");
    //    }
        
    //    List<Agencia> agencias = new ArrayList<Agencia>();
        
    //    for(PrestadorDeServico e : agenciasTemp) 
    //    {
    //    	Agencia agencia = new Agencia(e);
    //    	agencias.add(agencia);
    //    }
    //    return agencias;
    //}

    //@Override
    //public Optional<Agencia> findById(long id) {
    //    Optional<Agencia> agencia = agenciaRepository.findById(id);
    //    if(!agencia.isPresent()){
    //        throw new NotFoundAgenciaException("Agência não encontrada");
    //    }

        //Optional<Agencia> agencia = Optional.of(new Agencia(agenciaTemp.get()));
        
    //    return agencia;
    //}


    //@Override
    //@Transactional(readOnly = false)
    //public Agencia save(Agencia agencia) {
    //    if(agenciaRepository.existsByNome(agencia.getNome())){
    //        throw new RepeatedNameException("O nome já existe");
    //    }else if(agenciaRepository.existsByCnpj(agencia.getCnpj())) {
    //        throw new RepeatedCpfException("O CPF já existe");
    //    }else if(agenciaRepository.existsByEmail(agencia.getEmail())){
    //        throw new RepeatedEmailException("O E-mail já existe");
    //    }else if(agenciaRepository.existsByNomeUsuario(agencia.getNomeUsuario())){
    //        throw new RepeteadUsernameException("O nome de usuário já existe");
    //    }

    //    agencia.setSenha(getMd5(agencia.getSenha()));
    //    return agenciaRepository.save(agencia);
    //}

    //@Override
    //public boolean existsByCnpj(String cnpj) {
    //    return agenciaRepository.existsByCnpj(cnpj);
    //}

    //@Override
    //public boolean existsByEmail(String email) {
    //   return agenciaRepository.existsByEmail(email);
    //}

    @Override
    @Transactional(readOnly = false)
    public void updateNota(Restaurante restaurante, List<AvaliacaoPerUser> avaliacao){
        Double media1 = 0.0;
        Double media2 = 0.0;
        Double media3 = 0.0;
        Double media4 = 0.0;
        Double media5 = 0.0;
        

        for(AvaliacaoPerUser nota : avaliacao)
        {
            media1 += nota.getAvaliacaoAtendimento();
            media2 += nota.getAvaliacaoLimpeza();
            media3 += nota.getAvaliacaoRapidez();
            media4 += nota.getAvaliacaoConforto();
            media5 += nota.getAvaliacaoPreco();
        }

        media1 /= avaliacao.size();
        media2 /= avaliacao.size();
        media3 /= avaliacao.size();
        media4 /= avaliacao.size();
        media5 /= avaliacao.size();

        Double mediaGeral = (media1+media2+media3+media4+media5) / 5;
        restaurante.setNota(mediaGeral);
        
        
        restauranteRepository.save(restaurante);
    }

    
    @Override
	public List<ComentarioComNome> showComentarios(Restaurante restaurante)
	{
		List<AvaliacaoPerUser> avaliacoes = restaurante.getAvaliacoes();
		
		if(avaliacoes.isEmpty()) 
		{
			throw new NotFoundAvaliacaoException("O restaurante fornecido não possui avaliações");
		}

		List<ComentarioComNome> comentariosComNome = new ArrayList<ComentarioComNome>();

		for(AvaliacaoPerUser avaliacao : avaliacoes) 
		{
		    ComentarioComNome comentarioComNome = new ComentarioComNome();
		    comentarioComNome.nomeCliente = avaliacao.getCliente().getNome();
			comentarioComNome.comentario = avaliacao.getComentarios();
	
			comentariosComNome.add(comentarioComNome);
		}
		
		return comentariosComNome;
	}
    
    @Override
    public List<Double> showNotas(Restaurante restaurante){
        List<AvaliacaoPerUser> avaliacao = restaurante.getAvaliacoes();
    	
        if(avaliacao.isEmpty()){
            throw new NotFoundRestauranteException("Restaurante não encontrado");
        }
        
        Double media1 = 0.0;
        Double media2 = 0.0;
        Double media3 = 0.0;
        Double media4 = 0.0;
        Double media5 = 0.0;
        List<Double> notas = new ArrayList<>();

        for(AvaliacaoPerUser nota : avaliacao)
        {
            media1 += nota.getAvaliacaoAtendimento();
            media2 += nota.getAvaliacaoLimpeza();
            media3 += nota.getAvaliacaoRapidez();
            media4 += nota.getAvaliacaoConforto();
            media5 += nota.getAvaliacaoPreco();
        }

        if(avaliacao.isEmpty()){
            throw new NotFoundException("Não há avaliações ainda!");
        }

        media1 /= avaliacao.size();
        media2 /= avaliacao.size();
        media3 /= avaliacao.size();
        media4 /= avaliacao.size();
        media5 /= avaliacao.size();

        notas.add(media1);
        notas.add(media2);
        notas.add(media3);
        notas.add(media4);
        notas.add(media5);
        return notas;
    }

    public void salvarAvaliacao(AvaliacaoPerUser avaliacao) 
    {
    	avaliacaoPerUserRepository.save(avaliacao);
    }
    //@Override
    //@Transactional(readOnly = false)
    //public Agencia avaliarAgencia(@PathVariable long idCliente,
    //                                        @PathVariable long idAgencia, @RequestBody @Valid AvaliacaoPerUser avaliacao){
        // Coleta a agência passada como parâmetro.
    //    Optional<Agencia> agencia = agenciaRepository.findById(idAgencia);
        // Coleta o cliente passado como parâmetro
    //    Optional<Cliente> cliente = clienteRepository.findById(idCliente);

    //    if(!agencia.isPresent()){
    //        throw new NotFoundAgenciaException("Agência com ID " + idAgencia + " não encontrada ao avaliar!");
    //    }

    //    if(!cliente.isPresent()){
    //        throw new NotFoundClienteException("Cliente com ID " + idCliente + " não encontrado ao avaliar!");
    //    }

        //Agencia agencia = new Agencia(agenciaTemp.get());
        
        // Associamos os clientes e agência a avaliação e vice-versa

    //    avaliacao.setAgencia(agencia.get());
    //    avaliacao.setCliente(cliente.get());
    //    avaliacao.setIdAgencia(agencia.get().getId());
    //    avaliacao.setIdCliente(cliente.get().getId());


        // Chamando o service de avaliação para salvar o dado
    //    avaliacaoPerUserRepository.save(avaliacao);
    //    return agencia.get();
    //}

    //@Override
    //public boolean existsByNomeUsuario(String nomeUsuario) {
    //    return agenciaRepository.existsByNomeUsuario(nomeUsuario);
    //}

    //@Override
    //public boolean existsByNome(String nome) {
    //    return agenciaRepository.existsByNome(nome);
    //}

    //@Override
    //public Optional<Agencia> findByNomeUsuario(String nomeUsuario) {
    //    Agencia agencia = new Agencia(agenciaRepository.findByNomeUsuario(nomeUsuario).get());
    //	return Optional.of(agencia);
    //}

    //@Override
    //public Agencia checkLogin(Usuario usuario) {
    //    Optional<PrestadorDeServico> agenciaOp = agenciaRepository.findByNomeUsuario(usuario.getNomeUsuario());

    //    if (!agenciaOp.isPresent()) {
    //        throw  new NotFoundLoginException("O usuário não existe");
    //    } else if (!agenciaOp.get().getSenha().equals(getMd5(usuario.getSenha()))) {
    //        throw new WrongPasswordException("Senha incorreta");
    //    }
    //    return new Agencia(agenciaOp.get());
    //}

    //@Override
    //@Transactional(readOnly = false)
	//public void deleteById(long id) {
    //	agenciaRepository.deleteById(id);
	//}
}
