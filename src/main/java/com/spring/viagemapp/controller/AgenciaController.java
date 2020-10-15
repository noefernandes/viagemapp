package com.spring.viagemapp.controller;

import com.spring.viagemapp.error.*;
import com.spring.viagemapp.model.Agencia;
import com.spring.viagemapp.model.Cliente;
import com.spring.viagemapp.model.Usuario;
import com.spring.viagemapp.model.Viagem;
import com.spring.viagemapp.service.AgenciaService;
import com.spring.viagemapp.service.ViagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static com.spring.viagemapp.security.MD5.getMd5;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="/")
public class AgenciaController {
    @Autowired
    AgenciaService agenciaService;

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
        Optional<Agencia> temp = new Optional<Agencia>();

        try{
            temp = agenciaService.checkLogin(usuario);
        }catch(NotFoundLoginException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch(WrongPasswordException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<Agencia>(temp.get(), HttpStatus.OK);

    }

    @GetMapping("/agencias")
    public ResponseEntity<?> getAgencias(){
        List<Agencia> listaAgencias = new List<Agencia>();
        try {
            listaAgencias = agenciaService.findAll();
        }catch (NotFoundAgenciaException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Agencia>>(listaAgencias, HttpStatus.OK);
    }

    @GetMapping("/agencias/{id}")
    public ResponseEntity<?> getOneAgencia(@PathVariable("id") long id){
        Optional<Agencia> agenciaOp = new Optional<Agencia>();
        try{
            agenciaOp = agenciaService.findById(id);
        }catch (NotFoundAgenciaException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Agencia>(agenciaOp.get(), HttpStatus.OK);
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
