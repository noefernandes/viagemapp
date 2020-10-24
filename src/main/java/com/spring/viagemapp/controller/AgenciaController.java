package com.spring.viagemapp.controller;

import com.spring.viagemapp.error.*;
import com.spring.viagemapp.model.*;
import com.spring.viagemapp.service.AgenciaService;
import com.spring.viagemapp.service.AvaliacaoPerUserService;
import com.spring.viagemapp.service.ClienteService;
import com.spring.viagemapp.service.ViagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static com.spring.viagemapp.security.MD5.getMd5;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="/")
public class AgenciaController {
    @Autowired
    AgenciaService agenciaService;
    @Autowired
    ClienteService clienteService;
    @Autowired
    AvaliacaoPerUserService avaliacaoService;

    @PostMapping(value = "/cadastroAgencia")
    public ResponseEntity<?> cadastrarAgencia(@RequestBody @Valid Agencia agencia){
        Agencia temp = new Agencia();
        try{
        temp = agenciaService.save(agencia);
        }catch(RepeatedNameException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }catch(RepeatedCnpjException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }catch(RepeatedEmailException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }catch(RepeteadUsernameException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<Agencia>(temp, HttpStatus.CREATED);
    }

    @PostMapping(value = "/{id_cliente}/avaliarAgencia/{id_agencia}")
    public ResponseEntity<?> avaliarAgencia(@PathVariable ("id_cliente") long id_cliente,
    										@PathVariable ("id_agencia") long id_agencia,@RequestBody @Valid AvaliacaoPerUser avaliacao){
    	
    	System.out.println("Id Cliente: " + id_cliente + " Id Agencia: " + id_agencia);
    	
    	System.out.println("Nota1: " + avaliacao.getAvaliacaoAtendimento()
    					 + "\nNota2: " + avaliacao.getAvaliacaoConforto()
    					 + "\nNota3: " + avaliacao.getAvaliacaoLimpeza()
    					 + "\nNota4: " + avaliacao.getAvaliacaoPreco()
    					 + "\nNota5: " + avaliacao.getAvaliacaoRapidez()
    					 + "\nComentários: " + avaliacao.getComentarios());
       
    	Agencia agencia = agenciaService.findById(id_agencia).get();
    	
        Cliente cliente = clienteService.findById(id_cliente).get();
        
        avaliacao.setAgencia(agencia);
        avaliacao.setCliente(cliente);
        
        avaliacaoService.save(avaliacao);
       
        return new ResponseEntity<Agencia>(agencia,HttpStatus.OK);
    }
//Aqui será retornado uma lista de notas que serão na ordem: atendimento,limpeza,rapidez,
    //conforto,preço e nota geral
    @GetMapping(value = "/showNotas/{id_agencia}")
    public ResponseEntity<?> showNotas(@PathVariable ("id_agencia") long id_agencia){
        Agencia agencia = agenciaService.findById(id_agencia).get();
        List<Double> notas = new ArrayList<Double>();
        List<AvaliacaoPerUser> avaliacoes = avaliacaoService.findByAgencia(agencia);
        
        Double media1 = 0.0;
        Double media2 = 0.0;
        Double media3 = 0.0;
        Double media4 = 0.0;
        Double media5 = 0.0;
        
        for(AvaliacaoPerUser avaliacao : avaliacoes) 
        {
        	media1 += avaliacao.getAvaliacaoAtendimento();
        	media2 += avaliacao.getAvaliacaoLimpeza();
        	media3 += avaliacao.getAvaliacaoRapidez();
        	media4 += avaliacao.getAvaliacaoConforto();
        	media5 += avaliacao.getAvaliacaoPreco();
        }
        
        Double mediaGeral = (media1+media2+media3+media4+media5) / 5;
        
        notas.add(media1);
        notas.add(media2);
        notas.add(media3);
        notas.add(media4);
        notas.add(media5);
        notas.add(mediaGeral);
        
        return new ResponseEntity<List<Double>>(notas,HttpStatus.OK);
    }
//Aqui será retornado um hashmap, sendo a chave o nome do usuario que comentou e o valor o comentário
    @GetMapping(value = "/showComentarios/{id_agencia}")
    public ResponseEntity<?> showComentarios(@PathVariable ("id_agencia") long id_agencia){
        Agencia agencia = agenciaService.findById(id_agencia).get();
        HashMap<String,String> comentarios = new HashMap<String,String>();
        
        List<AvaliacaoPerUser> avaliacoes = avaliacaoService.findByAgencia(agencia);
        
        for(AvaliacaoPerUser avaliacao : avaliacoes) 
        {
        	String nomeUser = avaliacao.getCliente().getNome();
        	String coment = avaliacao.getComentarios();
        	
        	comentarios.put(nomeUser, coment);
        }
        
        return new ResponseEntity<HashMap<String,String>>(comentarios,HttpStatus.OK);
    }

    /*@PostMapping(value = "/loginAgencia")
    public ResponseEntity<?> realizarLogin(@RequestBody @Valid Usuario usuario){
        Optional<Agencia> agenciaOp = agenciaService.findByNomeUsuario(usuario.getNomeUsuario());

        if(!agenciaOp.isPresent()){
            return new ResponseEntity<>("O usuário não existe", HttpStatus.NOT_FOUND);
        }else if(!agenciaOp.get().getSenha().equals(getMd5(usuario.getSenha()))){
            return new ResponseEntity<>("Senha incorreta", HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<Agencia>(agenciaOp.get(), HttpStatus.OK);
    }*/

    @PostMapping(value = "/loginAgencia")
    public ResponseEntity<?> realizarLogin(@RequestBody @Valid Usuario usuario){
        Agencia temp = new Agencia();

        try{
            temp = agenciaService.checkLogin(usuario);
        }catch(NotFoundLoginException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch(WrongPasswordException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<Agencia>(temp, HttpStatus.OK);

    }

    @GetMapping("/agencias")
    public ResponseEntity<?> getAgencias(){
        List<Agencia> listaAgencias = new ArrayList<Agencia>();
        try {
            listaAgencias = agenciaService.findAll();
        }catch (NotFoundAgenciaException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Agencia>>(listaAgencias, HttpStatus.OK);
    }

    @GetMapping("/agencias/{id}")
    public ResponseEntity<?> getOneAgencia(@PathVariable("id") long id){
        Agencia agenciaOp;
        try{
            agenciaOp = agenciaService.findById(id).get();
        }catch (NotFoundAgenciaException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Agencia>(agenciaOp, HttpStatus.OK);
    }


   /* @GetMapping("/agencias")
    public ResponseEntity<?> getAgencias(){
        List<Agencia> listaAgencias = agenciaService.findAll();
        if(listaAgencias.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<List<Agencia>>(listaAgencias, HttpStatus.OK);
    }

    @GetMapping("/agencias/{id}")
    public ResponseEntity<?> getOneAgencia(@PathVariable("id") long id){
        Optional<Agencia> agenciaOp = agenciaService.findById(id);
        if(!agenciaOp.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Agencia>(agenciaOp.get(), HttpStatus.OK);
    }*/

    
}
