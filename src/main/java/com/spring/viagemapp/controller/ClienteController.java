package com.spring.viagemapp.controller;

import com.spring.viagemapp.error.*;
import com.spring.viagemapp.model.*;
import com.spring.viagemapp.service.AgenciaService;
import com.spring.viagemapp.service.ClienteService;
import com.spring.viagemapp.service.ClienteViagemService;
import com.spring.viagemapp.service.ViagemService;
import com.spring.viagemapp.utils.ClienteTags;

import com.spring.viagemapp.utils.ViagemComNome;
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
    ViagemService viagemService;
    @Autowired
    AgenciaService agenciaService;
    @Autowired
    ClienteViagemService clienteViagemService;


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

    @PostMapping(value="/{idCliente}/comprarViagem/{idViagem}")
    public ResponseEntity<?> comprarViagem(@PathVariable long idCliente, @PathVariable long idViagem){
        /*Cliente cliente = clienteService.findById(idCliente).get();
        Viagem viagem = viagemService.findById(idViagem).get();
        ClienteViagem clienteViagem = new ClienteViagem();
        clienteViagem.setCliente(cliente);
        clienteViagem.setIdCliente(idCliente);
        clienteViagem.setViagem(viagem);
        clienteViagem.setIdViagem(idViagem);
        cliente.getClienteViagem().add(clienteViagem);
        viagem.getClienteViagem().add(clienteViagem);
        //return new ResponseEntity<>(clienteService.resave(cliente), HttpStatus.OK);
        */

        int qtdClientes = clienteService.quantidadeDeClientes(idViagem);
        Viagem viagem = viagemService.findById(idViagem).get();

        if(qtdClientes < viagem.getCapacidade()) {
            ClienteViagem clienteViagem = new ClienteViagem();
            clienteViagem.setIdCliente(idCliente);
            clienteViagem.setIdViagem(idViagem);

            viagem.setQtdPassageiros(qtdClientes + 1);

            return new ResponseEntity<>(clienteViagemService.save(clienteViagem), HttpStatus.OK);
        }else{
            return  new ResponseEntity<>("Viagem cheia", HttpStatus.FORBIDDEN);
        }
    }
    
    @DeleteMapping(value="/{idCliente}/deletarViagemDoCliente/{idViagem}")
    public ResponseEntity<?> deletarViagemDoCliente(@PathVariable long idCliente, @PathVariable long idViagem){
        Cliente cliente;
        Viagem viagem;
        try{
            cliente = clienteService.findById(idCliente).get();
            viagem = viagemService.findById(idViagem).get();


            int qtdClientes = clienteService.quantidadeDeClientes(idViagem);
            viagem.setQtdPassageiros(qtdClientes - 1);

            clienteViagemService.deleteViagem(idCliente,idViagem);            
        }catch(NotFoundClienteException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }catch(NotFoundViagensException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }  

    
    @GetMapping(value = "/viagensCliente/{idCliente}")
    public ResponseEntity<?> getViagensDoCliente(@PathVariable long idCliente){
        List<Object[]> viagensObj = clienteService.getViagensDoCliente(idCliente);

        List<ViagemComNome> viagensComNome = clienteService.convert(viagensObj);

        return new ResponseEntity<>(viagensComNome, HttpStatus.OK);
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
            System.out.println(clientes.get(0).getNome());
        }catch(NotFoundClienteException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @GetMapping(value = "/{idViagem}/quantidadeDeClientes")
    public ResponseEntity<?> quantidadeDeClientes(@PathVariable long idViagem){
        return new ResponseEntity<>(clienteService.quantidadeDeClientes(idViagem), HttpStatus.OK);
    }

    /*@RequestMapping(value="/clientes/{id}", method=RequestMethod.GET)
    public ResponseEntity<?> getPostClienteDetails(@PathVariable("id") long id){
        Optional<Cliente> cliente;
        try{
            cliente = clienteService.findById(id);
        }catch (NotFoundClienteException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(cliente.get(),HttpStatus.OK);
    }*/






}
