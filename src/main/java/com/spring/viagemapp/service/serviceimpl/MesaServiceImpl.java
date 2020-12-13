package com.spring.viagemapp.service.serviceimpl;
import com.spring.viagemapp.error.*;
import com.spring.viagemapp.model.Mesa;
import com.spring.viagemapp.model.PrestadorDeServico;
import com.spring.viagemapp.model.Restaurante;
import com.spring.viagemapp.model.Servico;
import com.spring.viagemapp.repository.MesaRepository;
import com.spring.viagemapp.service.ClienteService;
import com.spring.viagemapp.service.MesaService;
import com.spring.viagemapp.service.RestauranteService;
import com.spring.viagemapp.utils.MesaComNome;
import com.spring.viagemapp.utils.MesaTags;
import com.spring.viagemapp.utils.ServicoTags;
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
public class MesaServiceImpl extends ServicoServiceImpl<Mesa> implements MesaService{

    @Autowired
    MesaRepository mesaRepository;

    @Autowired
    RestauranteService restauranteService;

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

    public List<MesaComNome> findAllSort(long idCliente){
        List<Mesa> mesas = mesaRepository.findAll();
        if(mesas.isEmpty()){
            throw new NotFoundException("Viagens não encontradas");
        }

        List<Mesa> listaMesas = findAll();

        List<MesaComNome> mesasCliente = clienteService.convert(clienteService.getMesasDoCliente(idCliente));
        List<Long> indices = new ArrayList<Long>();

        for(int i = 0; i < mesasCliente.size(); i++){
            indices.add(mesasCliente.get(i).mesa.getId());
        }

        List<MesaComNome> mesasComNome = new ArrayList<MesaComNome>();
        for(int i = 0; i < listaMesas.size(); i++){
            boolean pular = false;
            for(int j = 0; j < indices.size(); j++){
                if(listaMesas.get(i).getId() == indices.get(j)){
                    pular = true;
                }
            }

            if(pular){
                continue;
            }

            MesaComNome mesaComNome = new MesaComNome();
            mesaComNome.mesa = listaMesas.get(i);
            Restaurante restaurante = (Restaurante) restauranteService.findById(listaMesas.get(i).getIdRestaurante()).get();
            mesaComNome.nomeRestaurante = restaurante.getNome();
            mesaComNome.nota = restaurante.getNota();
            mesasComNome.add(mesaComNome);
        }
        
        mesasComNome.sort((MesaComNome rhs, MesaComNome lhs) -> 
        {
        	if(rhs.mesa.getTotalCompras() < lhs.mesa.getTotalCompras()) 
        	{
        		return 1;
        	}
        	else if(rhs.mesa.getTotalCompras() == lhs.mesa.getTotalCompras()) 
        	{
        		return 0;
        	}
        	
        	return -1;
        });

        return mesasComNome;
    }


    public List<Mesa> findAllByRestaurante(Restaurante restaurante){
        if(restaurante.getMesas().isEmpty()){
            throw new NotFoundMesasException("Esta agência não tem viagens");
        }
        
        List<Mesa> mesas = new ArrayList<Mesa>();
        
        for(Servico e : restaurante.getMesas())
        {
        	mesas.add((Mesa) e);
        }
        
        return mesas;
    }

    @Override
    public List<Mesa> getMesas(long id){

        Optional<Restaurante> restauranteOp = restauranteService.findById(id);

        if(!restauranteOp.isPresent()){
            throw new NotFoundRestauranteException("Agência de ID " + id + " não identificada. Não será possível" +
                    " encontrar suas viagens.");
        }

        
        List<Mesa> mesas = findAllByRestaurante(restauranteOp.get());
        return mesas;
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
    public void cadastrarMesa(MesaTags mesaTags, long id){
        Optional<Restaurante> restauranteOp = restauranteService.findById(id);
        if(!restauranteOp.isPresent()) {
            throw new NotFoundRestauranteException("Agência de ID " + id + " não encontrada. " +
                    "O cadastro da viagem não será possível.");
        }

        mesaTags.mesa.setRestaurante(restauranteOp.get());
        restauranteOp.get().addMesa(mesaTags.mesa);
        ServicoTags<Mesa> servicoTags = new ServicoTags<Mesa>();
        servicoTags.servico = mesaTags.mesa;
        servicoTags.tagString = mesaTags.tagString;
        save(servicoTags);
    }

	//@Override
	//public void delete(Viagem viagem) {
	//	viagemRepository.delete(viagem);
	//}

	@Override
    public void deletarMesa(long id){
        Optional<Mesa> mesa = mesaRepository.findById(id);
        if(!mesa.isPresent()){
            throw new NotFoundMesasException("Viagem de ID " + id + " não encontrada. " +
                    "A deleção não poderá ocorrer.");
        }

        mesaRepository.delete(mesa.get());
    }

    public List<String> getTagsMesa(long idMesa){
        return mesaRepository.getTagsServico(idMesa);
    }
}
