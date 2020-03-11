package com.spring.viagemapp.service.serviceimpl;

import com.spring.viagemapp.model.Agencia;
import com.spring.viagemapp.repository.AgenciaRepository;
import com.spring.viagemapp.service.AgenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgenciaServiceImpl implements AgenciaService {

    @Autowired
    AgenciaRepository agenciaRepository;

    @Override
    public List<Agencia> findAll() {
        return agenciaRepository.findAll();
    }

    @Override
    public Agencia findById(long id) {
        return agenciaRepository.findById(id).get();
    }

    @Override
    public Agencia save(Agencia agencia) {
        return agenciaRepository.save(agencia);
    }
}
