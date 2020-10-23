package com.spring.viagemapp.controller;

import com.spring.viagemapp.error.*;
import com.spring.viagemapp.model.*;
import com.spring.viagemapp.service.AgenciaService;
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

    ClienteService clienteService;

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

    @PostMapping(value = "{id_cliente}/avaliarAgencia/{id_agencia}")
    public ResponseEntity<?> avaliarAgencia(@PathVariable ("id_cliente") long id_cliente,
                                            @PathVariable ("id_agencia") long id_agencia,@RequestBody @Valid Avaliacoes avaliacoes){
        Optional<Agencia> agencia = agenciaService.findById(id_agencia);
        Optional<Cliente> cliente = Optional.ofNullable(clienteService.findById(id_cliente));

        agenciaService.addAvaliacao(agencia,cliente,avaliacoes);
        return new ResponseEntity<Agencia>(agencia,HttpStatus.OK);
    }
//Aqui será retornado uma lista de notas que serão na ordem: atendimento,limpeza,rapidez,
    //conforto,preço e nota geral
    @PostMapping(value = "/showNotas/{id_agencia}")
    public ResponseEntity<?> showNotas(@PathVariable ("id_agencia") long id_agencia){
        Optional<Agencia> agencia = agenciaService.findById(id_agencia);
        List<Double> notas = new List<Double>();
        notas = agenciaService.showNotas(agencia);
        return new ResponseEntity<List<Double>>(notas,HttpStatus.OK);
    }
//Aqui será retornado um hashmap, sendo a chave o nome do usuario que comentou e o valor o comentário
    @PostMapping(value = "/showComentarios/{id_agencia}")
    public ResponseEntity<?> showComentarios(@PathVariable ("id_agencia") long id_agencia){
        Optional<Agencia> agencia = agenciaService.findById(id_agencia);
        HashMap<String,String> comentarios = new HashMap<String,String>();
        comentarios = agenciaService.showCometarios(agencia);
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
        Optional<Agencia> agenciaOp;
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
