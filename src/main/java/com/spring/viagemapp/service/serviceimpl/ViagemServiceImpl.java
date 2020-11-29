package com.spring.viagemapp.service.serviceimpl;
import com.spring.viagemapp.error.*;
import com.spring.viagemapp.model.Agencia;
import com.spring.viagemapp.model.PrestadorDeServico;
import com.spring.viagemapp.model.Viagem;
import com.spring.viagemapp.repository.ViagemRepository;
import com.spring.viagemapp.service.AgenciaService;
import com.spring.viagemapp.service.ClienteService;
import com.spring.viagemapp.service.ViagemService;
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
public class ViagemServiceImpl implements ViagemService {

    @Autowired
    ViagemRepository viagemRepository;

    @Autowired
    AgenciaService agenciaService;

    @Autowired
    ClienteService clienteService;



    @Override
    public List<Viagem> findAll() {
        List<Viagem> viagens = viagemRepository.findAll();
        if(viagens.isEmpty()){
            throw new NotFoundAgenciaException("Viagens não encontradas");
        }

        return viagens;
    }

    public List<ViagemComNome> findAllSort(long idCliente){
        List<Viagem> viagens = viagemRepository.findAll();
        if(viagens.isEmpty()){
            throw new NotFoundException("Viagens não encontradas");
        }

        List<Viagem> listaViagens = findAll();

        List<ViagemComNome> viagensCliente = clienteService.convert(clienteService.getViagensDoCliente(idCliente));
        List<Long> indices = new ArrayList<Long>();

        for(int i = 0; i < viagensCliente.size(); i++){
            indices.add(viagensCliente.get(i).viagem.getIdv());
        }

        List<ViagemComNome> viagensComNome = new ArrayList<ViagemComNome>();
        for(int i = 0; i < listaViagens.size(); i++){
            boolean pular = false;
            for(int j = 0; j < indices.size(); j++){
                if(listaViagens.get(i).getIdv() == indices.get(j)){
                    pular = true;
                }
            }

            if(pular){
                continue;
            }

            ViagemComNome viagemComNome = new ViagemComNome();
            viagemComNome.viagem = listaViagens.get(i);
            Agencia agencia = (Agencia) agenciaService.findById(listaViagens.get(i).getIdAgencia()).get();
            viagemComNome.nomeAgencia = agencia.getNome();
            viagemComNome.nota = agencia.getNota();
            viagensComNome.add(viagemComNome);
        }
        
        viagensComNome.sort((ViagemComNome rhs, ViagemComNome lhs) -> 
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

        return viagensComNome;
    }


    public List<Viagem> findAllByAgencia(Agencia agencia){
        if(agencia.getViagens().isEmpty()){
            throw new NotFoundViagensException("Esta agência não tem viagens");
        }
        return agencia.getViagens();
    }

    @Override
    public List<Viagem> getViagens(long id){

        Optional<Agencia> agenciaOp = agenciaService.findById(id);

        if(!agenciaOp.isPresent()){
            throw new NotFoundAgenciaException("Agência de ID " + id + " não identificada. Não será possível" +
                    " encontrar suas viagens.");
        }

        
        List<Viagem> viagens = findAllByAgencia(agenciaOp.get());
        return viagens;
    }

    @Override
    public Optional<Viagem> findById(long id) {
        Optional<Viagem> viagem;
        viagem = viagemRepository.findById(id);
        if(!viagem.isPresent()){
            throw new NotFoundViagensException("Viagem não encontrada");
        }
        return viagem;
    }

    @Override
    public Viagem save(ViagemTags viagemTags) {
        List<String> tags = Arrays.asList(viagemTags.tagString.split(";"));
        for(int i = 0; i < tags.size(); i++) {
            //System.out.println("Valor1:" + tags.get(i));
            tags.set(i, StringUtils.stripAccents(tags.get(i)).trim().toLowerCase());
            //System.out.println("Valor2:" + tags.get(i));
        }
        viagemTags.viagem.setTags(tags);
        //System.out.println("local partida: " + viagemTags.viagem.getLocalPartida());
        return viagemRepository.save(viagemTags.viagem);
    }

    @Override
    public void cadastrarViagem(ViagemTags viagemTags, long id){
        Optional<Agencia> agenciaOp = agenciaService.findById(id);
        if(!agenciaOp.isPresent()) {
            throw new NotFoundAgenciaException("Agência de ID " + id + " não encontrada. " +
                    "O cadastro da viagem não será possível.");
        }

        viagemTags.viagem.setAgencia(agenciaOp.get());
        agenciaOp.get().addViagem(viagemTags.viagem);
        save(viagemTags);
    }

	@Override
	public void delete(Viagem viagem) {
		viagemRepository.delete(viagem);
	}

	@Override
    public void deletarViagem(long id){
        Optional<Viagem> viagem = viagemRepository.findById(id);
        if(!viagem.isPresent()){
            throw new NotFoundViagensException("Viagem de ID " + id + " não encontrada. " +
                    "A deleção não poderá ocorrer.");
        }

        viagemRepository.delete(viagem.get());
    }

	public boolean addNewTags(long id, ViagemTags viagemtags){
        if(findById(id).isPresent()) {
            List<String> tags = Arrays.asList(viagemtags.tagString.split(";"));

            viagemtags.viagem.addTags(tags);
            return true;
        }
        return false;

    }

    public List<String> getTagsViagem(long idViagem){
        return viagemRepository.getTagsViagem(idViagem);
    }
}
