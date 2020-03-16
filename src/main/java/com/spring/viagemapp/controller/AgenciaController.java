package com.spring.viagemapp.controller;

import com.spring.viagemapp.model.Agencia;
import com.spring.viagemapp.service.AgenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class AgenciaController {
    @Autowired
    AgenciaService agenciaService;

    @RequestMapping(value = "/agencias", method = RequestMethod.GET)
    public ModelAndView getAgencias(){
        ModelAndView mv = new ModelAndView("agencias");
        List<Agencia> agencias = agenciaService.findAll();
        mv.addObject("agencias", agencias);
        return mv;
    }
}
