package com.spring.viagemapp.service.serviceimpl;
import com.spring.viagemapp.error.*;
import com.spring.viagemapp.model.Hotel;
import com.spring.viagemapp.model.PrestadorDeServico;
import com.spring.viagemapp.model.Servico;
import com.spring.viagemapp.model.Viagem;
import com.spring.viagemapp.repository.ViagemRepository;
import com.spring.viagemapp.service.HotelService;
import com.spring.viagemapp.service.ClienteService;
import com.spring.viagemapp.service.ViagemService;
import com.spring.viagemapp.utils.ServicoTags;
import com.spring.viagemapp.utils.ViagemComNome;
import com.spring.viagemapp.utils.ViagemTags;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class QuartoServiceImpl extends ServicoServiceImpl<Quarto> implements QuartoService{

    @Autowired
    QuartoRepository quartoRepository;

    @Autowired
    HotelService hotelService;

    @Autowired
    ClienteService clienteService;



    //@Override
    //public List<Viagem> findAll() {
    //    List<Viagem> viagens = viagemRepository.findAll();
    //    if(viagens.isEmpty()){
    //        throw new NotFoundAgenciaException("Viagens não encontradas");
    //    }

    //    return viagens;
    //}

    public List<QuartoComNome> findAllSort(long idCliente){
        List<Quarto> quartos = quartoRepository.findAll();
        if(quartos.isEmpty()){
            throw new NotFoundException("Quartos não encontrados");
        }

        List<Quarto> listaViagens = findAll();

        List<QuartoComNome> quartosCliente = clienteService.convert(clienteService.getQuartosDoCliente(idCliente));
        List<Long> indices = new ArrayList<Long>();

        for(int i = 0; i < quartosCliente.size(); i++){
            indices.add(quartosCliente.get(i).viagem.getId());
        }

        List<QuartoComNome> quartosComNome = new ArrayList<QuartoComNome>();
        for(int i = 0; i < listaViagens.size(); i++){
            boolean pular = false;
            for(int j = 0; j < indices.size(); j++){
                if(listaViagens.get(i).getId() == indices.get(j)){
                    pular = true;
                }
            }

            if(pular){
                continue;
            }

            QuartoComNome quartoComNome = new QuartoComNome();
            quartoComNome.quarto = listaViagens.get(i);
            Hotel hotel = (Hotel) hotelService.findById(listaViagens.get(i).getIdHotel()).get();
            quartoComNome.nomeHotel = hotel.getNome();
            quartoComNome.nota = hotel.getNota();
            quartosComNome.add(quartoComNome);
        }
        
        quartosComNome.sort((QuartoComNome rhs, QuartoComNome lhs) -> 
        {
        	if(rhs.nota < lhs.nota) 
        	{
        		return 1;
        	}
        	else if(rhs.nota == lhs.nota) 
        	{
        		return 0;
        	}
        	
        	return -1;
        });

        return quartosComNome;
    }


    public List<Quarto> findAllByHotel(Hotel hotel){
        if(hotel.getViagens().isEmpty()){
            throw new NotFoundViagensException("Este hotel não tem quartos");
        }
        
        List<Quarto> quartos = new ArrayList<Quarto>();
        
        for(Servico e : hotel.getQuartos())
        {
        	quartos.add((Quarto) e);
        }
        
        return quartos;
    }

    @Override
    public List<Quarto> getViagens(long id){

        Optional<Hotel> hotelOp = hotelService.findById(id);

        if(!hotelOp.isPresent()){
            throw new NotFoundAgenciaException("Hotel de ID " + id + " não identificada. Não será possível" +
                    " encontrar seus hoteis.");
        }

        
        List<Quarto> quartos = findAllByHotel(hotelOp.get());
        return quartos;
    }

    //@Override
    //public Optional<Viagem> findById(long id) {
    //    Optional<Viagem> viagem;
    //    viagem = viagemRepository.findById(id);
    //    if(!viagem.isPresent()){
    //        throw new NotFoundViagensException("Viagem não encontrada");
    //    }
    //    return viagem;
    //}

    //@Override
    //public Viagem save(ViagemTags viagemTags) {
    //    List<String> tags = Arrays.asList(viagemTags.tagString.split(";"));
    //    for(int i = 0; i < tags.size(); i++) {
            //System.out.println("Valor1:" + tags.get(i));
    //        tags.set(i, StringUtils.stripAccents(tags.get(i)).trim().toLowerCase());
            //System.out.println("Valor2:" + tags.get(i));
    //    }
    //    viagemTags.viagem.setTags(tags);
        //System.out.println("local partida: " + viagemTags.viagem.getLocalPartida());
    //    return viagemRepository.save(viagemTags.viagem);
    //}

    @Override
    public void cadastrarQuarto(QuartoTags quartoTags, long id){
        Optional<Hotel> hotelOp = hotelService.findById(id);
        if(!hotelOp.isPresent()) {
            throw new NotFoundAgenciaException("Hotel de ID " + id + " não encontrada. " +
                    "O cadastro do quarto não será possível.");
        }

        quartoTags.quarto.setHotel(hotelOp.get());
        hotelOp.get().addViagem(quartoTags.viagem);
        ServicoTags<Viagem> servicoTags = new ServicoTags<Viagem>();
        servicoTags.servico = quartoTags.viagem;
        servicoTags.tagString = quartoTags.tagString;
        save(servicoTags);
    }

	//@Override
	//public void delete(Viagem viagem) {
	//	viagemRepository.delete(viagem);
	//}

	@Override
    public void deletarQuarto(long id){
        Optional<Quarto> quarto = quartoRepository.findById(id);
        if(!quarto.isPresent()){
            throw new NotFoundViagensException("Quarto de ID " + id + " não encontrada. " +
                    "A deleção não poderá ocorrer.");
        }

        quartoRepository.delete(quarto.get());
    }

    public List<String> getTagsQuarto(long idQuarto){
        return quartoRepository.getTagsServico(idQuarto);
    }
}
