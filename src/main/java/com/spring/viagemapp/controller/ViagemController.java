package com.spring.viagemapp.controller;

import com.spring.viagemapp.error.NotFoundAgenciaException;
import com.spring.viagemapp.error.NotFoundException;
import com.spring.viagemapp.error.NotFoundViagensException;
import com.spring.viagemapp.model.Agencia;
import com.spring.viagemapp.model.Viagem;
import com.spring.viagemapp.service.AgenciaService;
import com.spring.viagemapp.service.ClienteService;
import com.spring.viagemapp.service.ViagemService;
import com.spring.viagemapp.utils.ViagemComNome;
import com.spring.viagemapp.utils.ViagemTags;
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
public class ViagemController {
    @Autowired
    ViagemService viagemService;
    @Autowired
    ClienteService clienteService;
    @Autowired
    AgenciaService agenciaService;


    @GetMapping("/{idCliente}/viagensComNome")
    public ResponseEntity<?> getViagensComNomeDeAgencia(@PathVariable long idCliente){
        List<ViagemComNome> viagensComNome;
        try{
        	viagensComNome  = viagemService.findAllSort(idCliente);
        }catch (NotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(viagensComNome, HttpStatus.OK);
    }

    @GetMapping("/{id}/viagens")
    public ResponseEntity<?> getViagens(@PathVariable long id){
        List<Viagem> viagens;

        try{
            viagens = viagemService.getViagens(id);
        }catch(NotFoundViagensException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(viagens, HttpStatus.OK);
    }

    @PostMapping("/{id}/cadastrarViagem")
    public ResponseEntity<?> cadastrarViagem(@RequestBody ViagemTags viagemTags, @PathVariable long id){
        try{
            viagemService.cadastrarViagem(viagemTags, id);
        }catch(NotFoundAgenciaException e){
            //System.out.println("Entrou");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping("/viagens/{id}")
    public ResponseEntity<?> deletarViagem(@PathVariable long id){
        try{
            viagemService.deletarViagem(id);
        }catch(NotFoundViagensException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Agencia>(HttpStatus.OK);
    }


}
