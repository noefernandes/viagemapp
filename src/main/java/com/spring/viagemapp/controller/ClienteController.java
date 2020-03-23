package com.spring.viagemapp.controller;

import com.spring.viagemapp.model.Agencia;
import com.spring.viagemapp.model.Cliente;
import com.spring.viagemapp.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ClienteController {
    @Autowired
    ClienteService clienteService;

    @RequestMapping(value = "/cadastrarCliente", method = RequestMethod.GET)
    public String form(){
        return "formCadastroCliente";
    }

    @Transactional
    @RequestMapping(value = "/cadastrarCliente", method = RequestMethod.POST)
    public String cadastrarCliente(Cliente cliente){
        if(clienteService.existsByCpf(cliente.getCpf())) {
            System.err.println("O cliente já existe!");
        }else{
            clienteService.save(cliente);
        }
        return "redirect:/cadastrarCliente";
    }

    @RequestMapping(value = "/clientes", method = RequestMethod.GET)
    public List<Cliente> getClientes(){
        return clienteService.findAll();
        /*
        ModelAndView mv = new ModelAndView("clientes");
        List<Cliente> clientes = clienteService.findAll();
        mv.addObject("clientes", clientes);
        return mv;*/
    }

    @RequestMapping(value="/clientes/findById/{id}", method=RequestMethod.GET)
    public Cliente getPostClienteDetails(@PathVariable("id") long id){
        return clienteService.findById(id);
        /*
        ModelAndView mv = new ModelAndView("clienteDetails");
        Cliente clientes = clienteService.findById(id);
        mv.addObject("cliente", clientes);
        return mv;*/
    }

    @RequestMapping(value="/clientes/findByCpf/{cpf}", method=RequestMethod.GET)
    public Cliente getClienteDetails(@PathVariable("cpf") String cpf){
        Optional<Cliente> cliente;
        return clienteService.findByCpf(cpf);
        /*
        ModelAndView mv = new ModelAndView("clienteDetails");
        Cliente clientes = clienteService.findById(id);
        mv.addObject("cliente", clientes);
        return mv;*/
    }

    @RequestMapping("clientes/delete/{id}")
    public String deleteClienteById(@PathVariable long id,RedirectAttributes redirectAttrs){
        clienteService.deleteById(id);
        redirectAttrs.addFlashAttribute("message","Cliente excluído!");
        return "redirect:/clientes";
    }




}
