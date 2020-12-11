package com.spring.viagemapp.controller;

import com.spring.viagemapp.error.NotFoundRestauranteException;
import com.spring.viagemapp.error.NotFoundException;
import com.spring.viagemapp.error.NotFoundMesasException;
import com.spring.viagemapp.model.Restaurante;
import com.spring.viagemapp.model.Mesa;
import com.spring.viagemapp.service.RestauranteService;
import com.spring.viagemapp.service.ClienteService;
import com.spring.viagemapp.service.MesaService;
import com.spring.viagemapp.service.RestauranteService;
import com.spring.viagemapp.service.MesaService;
import com.spring.viagemapp.utils.MesaComNome;
import com.spring.viagemapp.utils.MesaTags;
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
public class MesaController {
    @Autowired
    MesaService mesaService;
    @Autowired
    ClienteService clienteService;
    @Autowired
    RestauranteService restauranteService;


    @GetMapping("/{idCliente}/viagensComNome")
    public ResponseEntity<?> getMesasComNomeDeRestaurante(@PathVariable long idCliente){
        List<MesaComNome> mesasComNome;
        try{
        	mesasComNome  = mesaService.findAllSort(idCliente);
        }catch (NotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(mesasComNome, HttpStatus.OK);
    }

    @GetMapping("/{id}/viagens")
    public ResponseEntity<?> getMesas(@PathVariable long id){
        List<Mesa> mesas;

        try{
            mesas = mesaService.getMesas(id);
        }catch(NotFoundMesasException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(mesas, HttpStatus.OK);
    }

    @PostMapping("/{id}/cadastrarViagem")
    public ResponseEntity<?> cadastrarMesa(@RequestBody MesaTags mesaTags, @PathVariable long id){
        try{
            mesaService.cadastrarMesa(mesaTags, id);
        }catch(NotFoundRestauranteException e){
            //System.out.println("Entrou");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping("/viagens/{id}")
    public ResponseEntity<?> deletarMesa(@PathVariable long id){
        try{
            mesaService.deletarMesa(id);
        }catch(NotFoundMesasException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Restaurante>(HttpStatus.OK);
    }


}
