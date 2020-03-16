package com.spring.viagemapp.controller;

import com.spring.viagemapp.model.Viagem;
import com.spring.viagemapp.service.ViagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ViagemController {
    @Autowired
    ViagemService viagemService;

    @RequestMapping(value = "/viagens", method = RequestMethod.GET)
    public ModelAndView getViagens(){
        ModelAndView mv = new ModelAndView("viagens");
        List<Viagem> viagens = viagemService.findAll();
        mv.addObject("viagens", viagens);
        return mv;
    }
}
