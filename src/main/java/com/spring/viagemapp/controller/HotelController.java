package com.spring.viagemapp.controller;

import com.spring.viagemapp.error.*;
import com.spring.viagemapp.model.*;
import com.spring.viagemapp.service.HotelService;
import com.spring.viagemapp.service.AvaliacaoPerUserService;
import com.spring.viagemapp.service.ClienteService;
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
public class HotelController {
    @Autowired
    HotelService hotelService;
    @Autowired
    ClienteService clienteService;
    @Autowired
    AvaliacaoPerUserService avaliacaoService;

    @PostMapping(value = "/cadastroHotel")
    public ResponseEntity<?> cadastrarHotel(@RequestBody @Valid Hotel hotel){
        Hotel temp = new Hotel();
        try{
        temp = (Hotel) hotelService.save(hotel);
        }catch(RepeatedNameException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }catch(RepeatedCnpjException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }catch(RepeatedEmailException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }catch(RepeteadUsernameException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<Hotel>(temp, HttpStatus.CREATED);
    }

    @PostMapping(value = "/{idCliente}/avaliarHotel/{idHotel}")
    public ResponseEntity<?> avaliarHotel(@PathVariable long idCliente,
    										@PathVariable long idHotel, @RequestBody @Valid AvaliacaoPerUser avaliacao){
        Hotel hotel;
    	try{
            hotel = hotelService.avaliarPrestador(idCliente, idHotel, avaliacao);
            hotelService.salvarAvaliacao(avaliacao);
        }catch(NotFoundAgenciaException e){
    	    return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch (NotFoundClienteException e){
    	    return  new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch (Exception e) 
    	{
        	return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    	}
       
        return new ResponseEntity<>(hotel, HttpStatus.OK);
    }

    @Transactional
    @PutMapping(value = "/updateToSortNotas/{id}")
    public ResponseEntity<?> updateToSortNotas(@PathVariable("id") long id){
        Hotel hotel;
        try{
            hotel = (Hotel) hotelService.findById(id).get();
            List<AvaliacaoPerUser> avaliacao = avaliacaoService.findByHotel(hotel);
            hotelService.updateNota(hotel,avaliacao);
        }catch (NotFoundAgenciaException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<Hotel>(hotel,HttpStatus.OK);
    }

//Aqui será retornado um hashmap, sendo a chave o nome do usuario que comentou e o valor o comentário
    @GetMapping(value = "/showComentarios/{idHotel}")
    public ResponseEntity<?> showComentarios(@PathVariable long idHotel){
        Hotel hotel = new Hotel();
        List<ComentarioComNome> comentarios;
        try{
            hotel = (Hotel) hotelService.findById(idHotel).get();
            try{
            	comentarios = hotelService.showComentarios(hotel);
            }catch(NotFoundAgenciaException e){
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
            }catch(NotFoundAvaliacaoException e)
            {
            	return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
            }

        }catch(NotFoundAgenciaException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND); 
        }
        
        
        
        
        return new ResponseEntity<>(comentarios, HttpStatus.OK);
    }

    @GetMapping(value = "/showNotas/{id_hotel}")
    public ResponseEntity<?> showNotas(@PathVariable ("id_hotel") long id_hotel){
        Hotel hotel;
        List<Double> avaliacao;
        try{
            hotel = (Hotel) hotelService.findById(id_hotel).get();
            avaliacao = hotelService.showNotas(hotel);
        }catch(NotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch(NotFoundAgenciaException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<List<Double>>(avaliacao,HttpStatus.OK);
    }



    @PostMapping(value = "/loginHotel")
    public ResponseEntity<?> realizarLogin(@RequestBody @Valid Usuario usuario){
        Hotel temp = new Hotel();

        try{
            temp = (Hotel) hotelService.checkLogin(usuario);
        }catch(NotFoundLoginException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch(WrongPasswordException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<Hotel>(temp, HttpStatus.OK);

    }

    @GetMapping("/hoteis")
    public ResponseEntity<?> getHoteis(){
        List<Hotel> listaHoteis = new ArrayList<Hotel>();
        try {
            listaHoteis = hotelService.findAll();
        }catch (NotFoundAgenciaException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Hotel>>(listaHoteis, HttpStatus.OK);
    }

    @GetMapping("/hoteis/{id}")
    public ResponseEntity<?> getOneHotel(@PathVariable("id") long id){
        Hotel hotelOp;
        try{
            hotelOp = (Hotel) hotelService.findById(id).get();
        }catch (NotFoundAgenciaException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Hotel>(hotelOp, HttpStatus.OK);
    }
    
}
