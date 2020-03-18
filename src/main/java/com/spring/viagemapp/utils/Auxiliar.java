package com.spring.viagemapp.utils;

import com.spring.viagemapp.model.Agencia;
import com.spring.viagemapp.model.Cliente;
import com.spring.viagemapp.model.ClienteViagem;
import com.spring.viagemapp.model.Viagem;
import com.spring.viagemapp.repository.ClienteRepository;
import com.spring.viagemapp.repository.AgenciaRepository;
import com.spring.viagemapp.repository.ClienteViagemRepository;
import com.spring.viagemapp.repository.ViagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class Auxiliar {

    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    AgenciaRepository agenciaRepository;
    @Autowired
    ViagemRepository viagemRepository;
    @Autowired
    ClienteViagemRepository clienteViagemRepository;

    //Esta anotação sempre executa primeiro na aplicação.
    //@PostConstruct
    public void saveClientes(){
        Agencia agencia1 = new Agencia();
        agencia1.setNome("Gol");
        agencia1.setCnpj("34432543");
        agencia1.setEmail("gol@fic.com");

        Agencia agencia2 = new Agencia();
        agencia1.setNome("Tam");
        agencia1.setCnpj("457864534");
        agencia1.setEmail("tam@fic.com");


        Viagem viagem1 = new Viagem();
        viagem1.setAgencia(agencia1);
        viagem1.setLocalPartida("Natal");
        viagem1.setLocalChegada("Sao Paulo");
        viagem1.setCapacidade(222);
        viagem1.setAvaliacao(7.5);
        viagem1.setPreco(859.99);

        Viagem viagem2 = new Viagem();
        viagem1.setAgencia(agencia1);
        viagem1.setLocalPartida("Natal");
        viagem1.setLocalChegada("Roma");
        viagem1.setCapacidade(345);
        viagem1.setAvaliacao(6.7);
        viagem1.setPreco(550.0);

        Cliente c1 = new Cliente();
        c1.setNome("Rafael Silva");
        c1.setCpf("3487364783");
        c1.setEmail("rafaelsilva@fic.com");

        Cliente c2 = new Cliente();
        c1.setNome("Douglas Santos");
        c1.setCpf("49579345734");
        c1.setEmail("douglassantos@fic.com");

        ClienteViagem cv1 = new ClienteViagem();
        cv1.setViagem(viagem1);
        cv1.setCliente(c1);
        cv1.setAvaliacaoViagem(6.7);

        ClienteViagem cv2 = new ClienteViagem();
        cv2.setViagem(viagem1);
        cv2.setCliente(c1);
        cv2.setAvaliacaoViagem(6.5);

        ArrayList<ClienteViagem> cv1A = new ArrayList<>();
        cv1A.add(cv1);
        c1.setClienteViagem(cv1A);

        ArrayList<Viagem> viagens1 = new ArrayList<>();
        viagens1.add(viagem1);
        agencia1.setViagens(viagens1);

        clienteViagemRepository.save(cv1);
        clienteRepository.save(c1);
        viagemRepository.save(viagem1);
        agenciaRepository.save(agencia1);
    }
}
