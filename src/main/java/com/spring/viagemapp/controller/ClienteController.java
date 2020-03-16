package com.spring.viagemapp.controller;

import com.spring.viagemapp.model.Cliente;
import com.spring.viagemapp.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ClienteController {
    @Autowired
    ClienteService clienteService;

    @RequestMapping(value = "/clientes", method = RequestMethod.GET)
    public ModelAndView getClientes(){
        ModelAndView mv = new ModelAndView("clientes");
        List<Cliente> clientes = clienteService.findAll();
        mv.addObject("clientes", clientes);
        return mv;
    }


}
