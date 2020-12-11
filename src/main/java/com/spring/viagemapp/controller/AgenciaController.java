package com.spring.viagemapp.controller;

import com.spring.viagemapp.error.*;
import com.spring.viagemapp.model.*;
import com.spring.viagemapp.service.AvaliacaoPerUserService;
import com.spring.viagemapp.service.ClienteService;
import com.spring.viagemapp.service.RestauranteService;
import com.spring.viagemapp.utils.ComentarioComNome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="/")
public class AgenciaController {
    @Autowired
    RestauranteService restauranteService;
    @Autowired
    ClienteService clienteService;
    @Autowired
    AvaliacaoPerUserService avaliacaoService;

    @PostMapping(value = "/cadastroAgencia")
    public ResponseEntity<?> cadastrarRestaurante(@RequestBody @Valid Restaurante restaurante){
        Restaurante temp = new Restaurante();
        try{
        temp = (Restaurante) restauranteService.save(restaurante);
        }catch(RepeatedNameException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }catch(RepeatedCnpjException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }catch(RepeatedEmailException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }catch(RepeteadUsernameException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(temp, HttpStatus.CREATED);
    }

    @PostMapping(value = "/{idCliente}/avaliarAgencia/{idAgencia}")
    public ResponseEntity<?> avaliarRestaurante(@PathVariable long idCliente,
    										@PathVariable long idRestaurante, @RequestBody @Valid AvaliacaoPerUser avaliacao){
        Restaurante restaurante;
    	try{
            restaurante = restauranteService.avaliarPrestador(idCliente, idRestaurante, avaliacao);
            restauranteService.salvarAvaliacao(avaliacao);
        }catch(NotFoundRestauranteException e){
    	    return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch (NotFoundClienteException e){
    	    return  new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch (Exception e) 
    	{
        	return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    	}
       
        return new ResponseEntity<>(restaurante, HttpStatus.OK);
    }

    @Transactional
    @PutMapping(value = "/updateToSortNotas/{id}")
    public ResponseEntity<?> updateToSortNotas(@PathVariable("id") long id){
        Restaurante restaurante;
        try{
            restaurante = (Restaurante) restauranteService.findById(id).get();
            List<AvaliacaoPerUser> avaliacao = avaliacaoService.findByRestaurante(restaurante);
            restauranteService.updateNota(restaurante, avaliacao);
        }catch (NotFoundRestauranteException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(restaurante, HttpStatus.OK);
    }

//Aqui será retornado um hashmap, sendo a chave o nome do usuario que comentou e o valor o comentário
    @GetMapping(value = "/showComentarios/{idAgencia}")
    public ResponseEntity<?> showComentarios(@PathVariable long idRestaurante){
        Restaurante restaurante = new Restaurante();
        List<ComentarioComNome> comentarios;
        try{
            restaurante = (Restaurante) restauranteService.findById(idRestaurante).get();
            try{
            	comentarios = restauranteService.showComentarios(restaurante);
            }catch(NotFoundRestauranteException e){
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
            }catch(NotFoundAvaliacaoException e)
            {
            	return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
            }

        }catch(NotFoundRestauranteException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND); 
        }
        
        
        
        
        return new ResponseEntity<>(comentarios, HttpStatus.OK);
    }

    @GetMapping(value = "/showNotas/{id_agencia}")
    public ResponseEntity<?> showNotas(@PathVariable long id_restaurante){
        Restaurante restaurante;
        List<Double> avaliacao;
        try{
            restaurante = (Restaurante) restauranteService.findById(id_restaurante).get();
            avaliacao = restauranteService.showNotas(restaurante);
        }catch(NotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch(NotFoundRestauranteException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(avaliacao, HttpStatus.OK);
    }



    @PostMapping(value = "/loginAgencia")
    public ResponseEntity<?> realizarLogin(@RequestBody @Valid Usuario usuario){
    	Restaurante temp = new Restaurante();

        try{
            temp = (Restaurante) restauranteService.checkLogin(usuario);
        }catch(NotFoundLoginException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch(WrongPasswordException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(temp, HttpStatus.OK);

    }

    @GetMapping("/agencias")
    public ResponseEntity<?> getAgencias(){
        List<Restaurante> listaAgencias = new ArrayList<Restaurante>();
        try {
            listaAgencias = restauranteService.findAll();
        }catch (NotFoundRestauranteException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(listaAgencias, HttpStatus.OK);
    }

    @GetMapping("/agencias/{id}")
    public ResponseEntity<?> getOneAgencia(@PathVariable("id") long id){
    	Restaurante agenciaOp;
        try{
            agenciaOp = (Restaurante) restauranteService.findById(id).get();
        }catch (NotFoundRestauranteException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(agenciaOp, HttpStatus.OK);
    }
    
}
