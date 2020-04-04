package com.spring.viagemapp.controller;

import com.spring.viagemapp.model.Agencia;
import com.spring.viagemapp.model.Cliente;
import com.spring.viagemapp.model.Viagem;
import com.spring.viagemapp.service.AgenciaService;
import com.spring.viagemapp.service.ClienteService;
import com.spring.viagemapp.service.ViagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.tags.EditorAwareTag;

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

    @GetMapping("/viagens")
    public ResponseEntity<?> getViagens(){
        List<Viagem> listaViagens = viagemService.findAll();
        if(listaViagens.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<List<Viagem>>(listaViagens, HttpStatus.OK);
    }

    @PostMapping("/viagens/{id}")
    public ResponseEntity<?> cadastrarViagem(@RequestBody Viagem viagem, @PathVariable long id){
        if(agenciaService.findById(id).isPresent()) {
            Agencia agencia = agenciaService.findById(id).get();
            viagem.setAgencia(agencia);
            viagemService.save(viagem);
            return new ResponseEntity<Agencia>(HttpStatus.OK);
        }

        return new ResponseEntity<>("A agência com ID " + id + " não existe", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/viagens/{id}")
    public ResponseEntity<?> deletarViagem(@PathVariable long id){
        Optional<Viagem> viagem = viagemService.findById(id);
        if(viagem.isPresent()){
            viagemService.delete(viagem.get());
            return new ResponseEntity<Agencia>(HttpStatus.OK);
        }

        return new ResponseEntity<>("A viagem com ID " + id + " não existe", HttpStatus.NOT_FOUND);
    }

    /*
    @PutMapping("/viagens/{id}")
    public ResponseEntity<?> cadastrarViagem(@RequestBody Viagem viagem, @PathVariable long id){
        Cliente cliente = clienteService.findById(id);
    }

    @RequestMapping(value="/viagens/{id}", method=RequestMethod.GET)
    public ModelAndView getPostViagemDetails2(@PathVariable("id") long id){
        ModelAndView mv = new ModelAndView("viagemDetails2");
        Viagem viagens = viagemService.findById(id);
        mv.addObject("viagem", viagens);
        return mv;
    }*/
}
