package com.spring.viagemapp.controller;

import com.spring.viagemapp.model.Agencia;
import com.spring.viagemapp.model.Cliente;
import com.spring.viagemapp.model.Usuario;
import com.spring.viagemapp.model.Viagem;
import com.spring.viagemapp.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static com.spring.viagemapp.security.MD5.getMd5;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="/")
public class ClienteController {
    @Autowired
    ClienteService clienteService;

    @Transactional
    @PostMapping(value = "/cadastroCliente")
    public ResponseEntity<?> cadastrarCliente(@RequestBody @Valid Cliente cliente){
        if(clienteService.existsByNome(cliente.getNome())){
            return new ResponseEntity<>("O nome já existe", HttpStatus.FORBIDDEN);
        }else if(clienteService.existsByCpf(cliente.getCpf())) {
            return new ResponseEntity<>("O CPF já existe", HttpStatus.FORBIDDEN);
        }else if(clienteService.existsByEmail(cliente.getEmail())){
            return new ResponseEntity<>("O E-mail já existe", HttpStatus.FORBIDDEN);
        }else if(clienteService.existsByNomeUsuario(cliente.getNomeUsuario())){
            return new ResponseEntity<>("O nome de usuário já existe", HttpStatus.FORBIDDEN);
        }


        cliente.setSenha(getMd5(cliente.getSenha()));
        return new ResponseEntity<Cliente>(clienteService.save(cliente), HttpStatus.CREATED);
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
    }

    @PostMapping(value = "/loginCliente")
    public ResponseEntity<?> realizarLogein(@RequestBody @Valid Usuario usuario){
        Optional<Cliente> cliente = clienteService.findByNomeUsuario(usuario.getNomeUsuario());

        if(!cliente.isPresent()){
            return new ResponseEntity<>("O usuário não existe", HttpStatus.NOT_FOUND);
        }else if(!cliente.get().getSenha().equals(getMd5(usuario.getSenha()))){
            return new ResponseEntity<>("Senha incorreta", HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<Cliente>(cliente.get(), HttpStatus.OK);
    }


    @PutMapping(value="clientes/{id}")
    public String updateCliente(@PathVariable("id") long id, @Valid Cliente cliente, RedirectAttributes redirectAttrs) {

        clienteService.save(cliente);
        redirectAttrs.addFlashAttribute("message","Cliente excluído!");
        return "redirect:/clientes/{id}";
    }







}
