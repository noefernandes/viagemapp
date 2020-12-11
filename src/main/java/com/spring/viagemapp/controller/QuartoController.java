package com.spring.viagemapp.controller;

import com.spring.viagemapp.error.NotFoundAgenciaException;
import com.spring.viagemapp.error.NotFoundException;
import com.spring.viagemapp.error.NotFoundViagensException;
import com.spring.viagemapp.model.Hotel;
import com.spring.viagemapp.model.Quarto;
import com.spring.viagemapp.service.HotelService;
import com.spring.viagemapp.service.ClienteService;
import com.spring.viagemapp.service.QuartoService;
import com.spring.viagemapp.utils.QuartoComNome;
import com.spring.viagemapp.utils.QuartoTags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RequestMapping(value="/")
@RestController
public class QuartoController {
    @Autowired
    QuartoService quartoService;
    @Autowired
    ClienteService clienteService;
    @Autowired
    Service agenciaService;


    @GetMapping("/{idCliente}/quartosComNome")
    public ResponseEntity<?> getQuartosComNomeDeHotel(@PathVariable long idCliente){
        List<QuartoComNome> quartosComNome;
        try{
        	quartoComNome  = quartoService.findAllSort(idCliente);
        }catch (NotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(quartosComNome, HttpStatus.OK);
    }

    @GetMapping("/{id}/quartos")
    public ResponseEntity<?> getQuartos(@PathVariable long id){
        List<Quarto> quartos;

        try{
            quartos = quartoService.getQuartos(id);
        }catch(NotFoundViagensException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(quartos, HttpStatus.OK);
    }

    @PostMapping("/{id}/cadastrarQuarto")
    public ResponseEntity<?> cadastrarQuarto(@RequestBody QuartoTags quartoTags, @PathVariable long id){
        try{
            quartoService.cadastrarQuarto(quartoTags, id);
        }catch(NotFoundAgenciaException e){
            //System.out.println("Entrou");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping("/quartos/{id}")
    public ResponseEntity<?> deletarQuarto(@PathVariable long id){
        try{
            quartoService.deletarViagem(id);
        }catch(NotFoundViagensException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Hotel>(HttpStatus.OK);
    }


}
