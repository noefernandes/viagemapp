package com.spring.viagemapp.controller;

import com.spring.viagemapp.error.*;
import com.spring.viagemapp.model.*;
import com.spring.viagemapp.service.HotelService;
import com.spring.viagemapp.service.ClienteService;
import com.spring.viagemapp.service.ClienteQuartoService;
import com.spring.viagemapp.service.QuartoService;
import com.spring.viagemapp.utils.ClienteTags;

import com.spring.viagemapp.utils.QuartoComNome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigInteger;
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
    @Autowired
    ViagemService quartoService;
    @Autowired
    AgenciaService hotelService;
    @Autowired
    ClienteViagemService clienteQuartoService;


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

    @PostMapping(value="/{idCliente}/comprarQuarto/{idQuarto}")
    public ResponseEntity<?> comprarViagem(@PathVariable long idCliente, @PathVariable long idQuarto){
        ClienteQuarto clienteQuarto;
        try{
            clienteQuarto = clienteQuartoService.comprarViagem(idCliente, idQuarto);
        }catch(CapacityException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(clienteQuarto, HttpStatus.OK);
    }
    
    @DeleteMapping(value="/{idCliente}/deletarQuartoDoCliente/{idQuarto}")
    public ResponseEntity<?> deletarViagemDoCliente(@PathVariable long idCliente, @PathVariable long idQuarto){
        try{
            clienteQuartoService.deleteClienteQuarto(idCliente,idQuarto);
        }catch(NotFoundClienteException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }catch(NotFoundViagensException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }  

    
    @GetMapping(value = "/quartosCliente/{idCliente}")
    public ResponseEntity<?> getQuartosDoCliente(@PathVariable long idCliente){
        return new ResponseEntity<>(clienteService.getQuartosDoClienteComNome(idCliente), HttpStatus.OK);
    }

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

    @GetMapping(value = "/{idQuarto}/quantidadeDeClientes")
    public ResponseEntity<?> quantidadeDeClientes(@PathVariable long idViagem){
        return new ResponseEntity<>(clienteService.quantidadeDeClientes(idQuarto), HttpStatus.OK);
    }



}
