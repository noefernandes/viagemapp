package com.spring.viagemapp.controller;

import com.spring.viagemapp.model.Agencia;
import com.spring.viagemapp.model.Cliente;
import com.spring.viagemapp.service.AgenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class AgenciaController {
    @Autowired
    AgenciaService agenciaService;

    @RequestMapping(value = "/cadastrarAgencia", method = RequestMethod.GET)
    public String form(){
        return "formCadastroAgencia";
    }

    @Transactional
    @RequestMapping(value = "/cadastrarAgencia", method = RequestMethod.POST)
    public String cadastrarAgencia(Agencia agencia){
        if(agenciaService.existsByCnpj(agencia.getCnpj())) {
            System.err.println("A agência já existe!");
        }else{
            agenciaService.save(agencia);
        }
        return "redirect:/cadastrarAgencia";
    }

    @RequestMapping(value = "/agencias", method = RequestMethod.GET)
    public ModelAndView getAgencias(){
        ModelAndView mv = new ModelAndView("agencia");
        List<Agencia> agencias = agenciaService.findAll();
        mv.addObject("agencias", agencias);
        return mv;
    }


    
}
