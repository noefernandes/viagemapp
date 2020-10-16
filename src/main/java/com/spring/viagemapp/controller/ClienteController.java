package com.spring.viagemapp.controller;

import com.spring.viagemapp.error.*;
import com.spring.viagemapp.model.Agencia;
import com.spring.viagemapp.model.Cliente;
import com.spring.viagemapp.model.Usuario;
import com.spring.viagemapp.service.ClienteService;
import com.spring.viagemapp.utils.ClienteTags;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.spring.viagemapp.security.MD5.getMd5;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="/")
public class ClienteController {
    @Autowired
    ClienteService clienteService;

   /* @PostMapping(value = "/cadastrarCliente")
    public ResponseEntity<?> cadastrarCliente(@RequestBody @Valid ClienteTags clienteTags){
        if(clienteService.existsByNome(clienteTags.cliente.getNome())){
            return new ResponseEntity<>("O nome já existe", HttpStatus.FORBIDDEN);
        }else if(clienteService.existsByCpf(clienteTags.cliente.getCpf())) {
            return new ResponseEntity<>("O CPF já existe", HttpStatus.FORBIDDEN);
        }else if(clienteService.existsByEmail(clienteTags.cliente.getEmail())){
            return new ResponseEntity<>("O E-mail já existe", HttpStatus.FORBIDDEN);
        }else if(clienteService.existsByNomeUsuario(clienteTags.cliente.getNomeUsuario())){
            return new ResponseEntity<>("O nome de usuário já existe", HttpStatus.FORBIDDEN);
        }


        clienteTags.cliente.setSenha(getMd5(clienteTags.cliente.getSenha()));
        return new ResponseEntity<Cliente>(clienteService.save(clienteTags), HttpStatus.CREATED);
    }*/

    @PostMapping(value = "/cadastroCliente")
    public ResponseEntity<?> cadastrarCliente(@RequestBody @Valid ClienteTags clienteTags){
        Cliente temp = new Cliente();
        try{
            temp = clienteService.save(clienteTags);
        }catch(RepeatedNameException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }catch(RepeatedCnpjException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }catch(RepeatedEmailException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }catch(RepeteadUsernameException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<Cliente>(temp, HttpStatus.CREATED);
    }

    /*@PostMapping(value = "/loginCliente")
    public ResponseEntity<?> realizarLogin(@RequestBody @Valid Usuario usuario){
        Optional<Cliente> clienteOp = clienteService.findByNomeUsuario(usuario.getNomeUsuario());

        if(!clienteOp.isPresent()){
            return new ResponseEntity<>("O usuário não existe", HttpStatus.NOT_FOUND);
        }else if(!clienteOp.get().getSenha().equals(getMd5(usuario.getSenha()))){
            return new ResponseEntity<>("Senha incorreta", HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<Cliente>(clienteOp.get(), HttpStatus.OK);
    }

    @RequestMapping(value = "/clientes", method = RequestMethod.GET)
    public ResponseEntity<?> getClientes(){
        List<Cliente> clientes =  clienteService.findAll();
        if(clientes.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<List<Cliente>>(clientes, HttpStatus.OK);
    }

    @RequestMapping(value="/clientes/{id}", method=RequestMethod.GET)
    public ResponseEntity<?> getPostClienteDetails(@PathVariable("id") long id){
       Optional<Cliente> cliente = clienteService.findById(id);
       if(!cliente.isPresent()){
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }

       return new ResponseEntity<Cliente>(cliente.get(),HttpStatus.OK);
    }*/

    @PostMapping(value = "/loginCliente")
    public ResponseEntity<?> realizarLogin(@RequestBody @Valid Usuario usuario){
        Cliente temp = new Cliente();
        try{
            temp = clienteService.checkLogin(usuario);
        }catch(NotFoundLoginException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch(WrongPasswordException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<Cliente>(temp, HttpStatus.OK);
    }


    @RequestMapping(value = "/clientes", method = RequestMethod.GET)
    public ResponseEntity<?> getClientes(){
        List<Cliente> clientes =  new ArrayList<Cliente>();
        try{
            clientes = clienteService.findAll();
        }catch(NotFoundClienteException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @RequestMapping(value="/clientes/{id}", method=RequestMethod.GET)
    public ResponseEntity<?> getPostClienteDetails(@PathVariable("id") long id){
        Optional<Cliente> cliente;
        try{
            cliente = clienteService.findById(id);
        }catch (NotFoundClienteException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(cliente.get(),HttpStatus.OK);
    }
    
    /*@PostMapping("clientes/{id}/tag")
    public ResponseEntity<?> cadastrarTags(@RequestBody ClienteTags clienteTags, @PathVariable long id){
        if(clienteService.addNewTags(id,clienteTags)){
            return new ResponseEntity<Agencia>(HttpStatus.OK);
        }
        return new ResponseEntity<>("O cliente com ID" + id + "não existe",HttpStatus.NOT_FOUND);
    }
     */





}
