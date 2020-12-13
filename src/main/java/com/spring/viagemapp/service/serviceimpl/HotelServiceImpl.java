package com.spring.viagemapp.service.serviceimpl;

import com.spring.viagemapp.error.*;
import com.spring.viagemapp.model.Hotel;
import com.spring.viagemapp.model.AvaliacaoPerUser;
import com.spring.viagemapp.model.Cliente;
import com.spring.viagemapp.model.PrestadorDeServico;
import com.spring.viagemapp.model.Usuario;
import com.spring.viagemapp.repository.HotelRepository;
import com.spring.viagemapp.repository.AvaliacaoPerUserRepository;
import com.spring.viagemapp.repository.ClienteRepository;
import com.spring.viagemapp.service.HotelService;
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
public class HotelServiceImpl extends PrestadorDeServicoServiceImpl<Hotel, AvaliacaoPerUser> implements HotelService{

    @Autowired
    HotelRepository hotelRepository;
	
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    AvaliacaoPerUserRepository avaliacaoPerUserRepository;

    //@Override
    //public List<Hotel> findAll() {
    //    List<Hotel> hotelsTemp = hotelRepository.findAll();
    //    if(hotelsTemp.isEmpty()){
    //        throw new NotFoundHotelException("Agências não encontradas");
    //    }
        
    //    List<Hotel> hotels = new ArrayList<Hotel>();
        
    //    for(PrestadorDeServico e : hotelsTemp) 
    //    {
    //    	Hotel hotel = new Hotel(e);
    //    	hotels.add(hotel);
    //    }
    //    return hotels;
    //}

    //@Override
    //public Optional<Hotel> findById(long id) {
    //    Optional<Hotel> hotel = hotelRepository.findById(id);
    //    if(!hotel.isPresent()){
    //        throw new NotFoundHotelException("Agência não encontrada");
    //    }

        //Optional<Hotel> hotel = Optional.of(new Hotel(hotelTemp.get()));
        
    //    return hotel;
    //}


    //@Override
    //@Transactional(readOnly = false)
    //public Hotel save(Hotel hotel) {
    //    if(hotelRepository.existsByNome(hotel.getNome())){
    //        throw new RepeatedNameException("O nome já existe");
    //    }else if(hotelRepository.existsByCnpj(hotel.getCnpj())) {
    //        throw new RepeatedCpfException("O CPF já existe");
    //    }else if(hotelRepository.existsByEmail(hotel.getEmail())){
    //        throw new RepeatedEmailException("O E-mail já existe");
    //    }else if(hotelRepository.existsByNomeUsuario(hotel.getNomeUsuario())){
    //        throw new RepeteadUsernameException("O nome de usuário já existe");
    //    }

    //    hotel.setSenha(getMd5(hotel.getSenha()));
    //    return hotelRepository.save(hotel);
    //}

    //@Override
    //public boolean existsByCnpj(String cnpj) {
    //    return hotelRepository.existsByCnpj(cnpj);
    //}

    //@Override
    //public boolean existsByEmail(String email) {
    //   return hotelRepository.existsByEmail(email);
    //}

    @Override
    @Transactional(readOnly = false)
    public void updateNota(Hotel hotel, List<AvaliacaoPerUser> avaliacao){
        Double media1 = 0.0;
        Double media2 = 0.0;
        Double media3 = 0.0;
        Double media4 = 0.0;
        Double media5 = 0.0;
        

        for(AvaliacaoPerUser nota : avaliacao)
        {
            media1 += nota.getAvaliacaoAtendimento();
            media2 += nota.getAvaliacaoLimpeza();
            media3 += nota.getAvaliacaoLocalidade();
            media4 += nota.getAvaliacaoConforto();
            media5 += nota.getAvaliacaoPreco();
        }

        media1 /= avaliacao.size();
        media2 /= avaliacao.size();
        media3 /= avaliacao.size();
        media4 /= avaliacao.size();
        media5 /= avaliacao.size();

        Double mediaGeral = (media1+media2+media3+media4+media5) / 5;
        hotel.setNota(mediaGeral);
        
        
        hotelRepository.save(hotel);
    }

    
    @Override
	public List<ComentarioComNome> showComentarios(Hotel hotel)
	{
		List<AvaliacaoPerUser> avaliacoes = hotel.getAvaliacoes();
		
		if(avaliacoes.isEmpty()) 
		{
			throw new NotFoundAvaliacaoException("O hotel fornecido não possui avaliações");
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
    public List<Double> showNotas(Hotel hotel){
        List<AvaliacaoPerUser> avaliacao = hotel.getAvaliacoes();
    	
        if(avaliacao.isEmpty()){
            throw new NotFoundAgenciaException("Hotel não encontrada");
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
            media3 += nota.getAvaliacaoLocalidade();
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
    //public Hotel avaliarHotel(@PathVariable long idCliente,
    //                                        @PathVariable long idHotel, @RequestBody @Valid AvaliacaoPerUser avaliacao){
        // Coleta a agência passada como parâmetro.
    //    Optional<Hotel> hotel = hotelRepository.findById(idHotel);
        // Coleta o cliente passado como parâmetro
    //    Optional<Cliente> cliente = clienteRepository.findById(idCliente);

    //    if(!hotel.isPresent()){
    //        throw new NotFoundHotelException("Agência com ID " + idHotel + " não encontrada ao avaliar!");
    //    }

    //    if(!cliente.isPresent()){
    //        throw new NotFoundClienteException("Cliente com ID " + idCliente + " não encontrado ao avaliar!");
    //    }

        //Hotel hotel = new Hotel(hotelTemp.get());
        
        // Associamos os clientes e agência a avaliação e vice-versa

    //    avaliacao.setHotel(hotel.get());
    //    avaliacao.setCliente(cliente.get());
    //    avaliacao.setIdHotel(hotel.get().getId());
    //    avaliacao.setIdCliente(cliente.get().getId());


        // Chamando o service de avaliação para salvar o dado
    //    avaliacaoPerUserRepository.save(avaliacao);
    //    return hotel.get();
    //}

    //@Override
    //public boolean existsByNomeUsuario(String nomeUsuario) {
    //    return hotelRepository.existsByNomeUsuario(nomeUsuario);
    //}

    //@Override
    //public boolean existsByNome(String nome) {
    //    return hotelRepository.existsByNome(nome);
    //}

    //@Override
    //public Optional<Hotel> findByNomeUsuario(String nomeUsuario) {
    //    Hotel hotel = new Hotel(hotelRepository.findByNomeUsuario(nomeUsuario).get());
    //	return Optional.of(hotel);
    //}

    //@Override
    //public Hotel checkLogin(Usuario usuario) {
    //    Optional<PrestadorDeServico> hotelOp = hotelRepository.findByNomeUsuario(usuario.getNomeUsuario());

    //    if (!hotelOp.isPresent()) {
    //        throw  new NotFoundLoginException("O usuário não existe");
    //    } else if (!hotelOp.get().getSenha().equals(getMd5(usuario.getSenha()))) {
    //        throw new WrongPasswordException("Senha incorreta");
    //    }
    //    return new Hotel(hotelOp.get());
    //}

    //@Override
    //@Transactional(readOnly = false)
	//public void deleteById(long id) {
    //	hotelRepository.deleteById(id);
	//}
}
