package com.spring.viagemapp.controller;

import com.spring.viagemapp.model.Agencia;
import com.spring.viagemapp.model.Cliente;
import com.spring.viagemapp.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
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
    public ModelAndView getClientes(){
        ModelAndView mv = new ModelAndView("clientes");
        List<Cliente> clientes = clienteService.findAll();
        mv.addObject("clientes", clientes);
        return mv;
    }

    @RequestMapping(value="/clientes/{id}", method=RequestMethod.GET)
    public ModelAndView getPostClienteDetails2(@PathVariable("id") long id){
        ModelAndView mv = new ModelAndView("clienteDetails2");
        Cliente clientes = clienteService.findById(id);
        mv.addObject("cliente", clientes);
        return mv;
    }




}
