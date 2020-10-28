package com.spring.viagemapp.controller;

import com.spring.viagemapp.error.*;
import com.spring.viagemapp.model.*;
import com.spring.viagemapp.service.AgenciaService;
import com.spring.viagemapp.service.AvaliacaoPerUserService;
import com.spring.viagemapp.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


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
       
    	// Coleta a agência passada como parâmetro.
    	Agencia agencia = agenciaService.findById(id_agencia).get();
    	// Coleta o cliente passado como parâmetro
        Cliente cliente = clienteService.findById(id_cliente).get();
        
        // Associamos os clientes e agência a avaliação e vice-versa
        avaliacao.setAgencia(agencia);
        avaliacao.setCliente(cliente);
        
        // Chamando o service de avaliação para salvar o dado
        avaliacaoService.save(avaliacao);
       
        return new ResponseEntity<Agencia>(agencia,HttpStatus.OK);
    }

    @PutMapping(value = "/updateToSortNotas/{id}")
    public ResponseEntity<?> updateToSortNotas(@PathVariable("id") long id){
        Agencia agencia;

        try{
            agencia = agenciaService.findById(id).get();
            List<AvaliacaoPerUser> avaliacao = avaliacaoService.findByAgencia(agencia);
            agenciaService.updateNota(agencia,avaliacao);
        }catch (NotFoundAgenciaException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<Agencia>(agencia,HttpStatus.OK);
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
