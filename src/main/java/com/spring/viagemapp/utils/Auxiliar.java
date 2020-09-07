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
import org.springframework.transaction.annotation.Transactional;

//import javax.annotation.PostConstruct;
import javax.annotation.PostConstruct;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

//@Component
public class Auxiliar {

    /*@Autowired
    ClienteRepository clienteRepository;
    @Autowired
    AgenciaRepository agenciaRepository;
    @Autowired
    ViagemRepository viagemRepository;
    @Autowired
    ClienteViagemRepository clienteViagemRepository;
    */

    //Esta anotação sempre executa primeiro na aplicação.
    //@PostConstruct
    public void saveClientes(){





        /*Agencia agencia1 = new Agencia();
        agencia1.setNome("Gol");
        agencia1.setCnpj("34432543");
        agencia1.setEmail("gol@fic.com");

        Agencia agencia2 = new Agencia();
        agencia2.setNome("Tam");
        agencia2.setCnpj("45786456");
        agencia2.setEmail("tam@fic.com");



        Viagem viagem1 = new Viagem();
        viagem1.setAgencia(agencia1);
        viagem1.setLocalPartida("Natal");
        viagem1.setLocalChegada("Sao Paulo");
        viagem1.setCapacidade(222);
        viagem1.setAvaliacao(7.5);
        viagem1.setPreco(859.99);

        Viagem viagem2 = new Viagem();
        viagem2.setAgencia(agencia1);
        viagem2.setLocalPartida("Natal");
        viagem2.setLocalChegada("Roma");
        viagem2.setCapacidade(345);
        viagem2.setAvaliacao(6.7);
        viagem2.setPreco(550.0);

        Viagem viagem3 = new Viagem();
        viagem3.setAgencia(agencia2);
        viagem3.setLocalPartida("Rio de Janeiro");
        viagem3.setLocalChegada("Porto Alegre");
        viagem3.setCapacidade(250);
        viagem3.setAvaliacao(8.3);
        viagem3.setPreco(1200.0);

        Viagem viagem4 = new Viagem();
        viagem4.setAgencia(agencia2);
        viagem4.setLocalPartida("Salvador");
        viagem4.setLocalChegada("Minas Gerais");
        viagem4.setCapacidade(300);
        viagem4.setAvaliacao(506);
        viagem4.setPreco(480.0);

        Cliente c1 = new Cliente();
        c1.setNome("Rafael Silva");
        c1.setCpf("3487364783");
        c1.setEmail("rafaelsilva@fic.com");

        Cliente c2 = new Cliente();
        c2.setNome("Douglas Santos");
        c2.setCpf("49579345734");
        c2.setEmail("douglassantos@fic.com");

        Cliente c3 = new Cliente();
        c3.setNome("Manoel Lima");
        c3.setCpf("96937458263");
        c3.setEmail("manellima@fic.com");

        ClienteViagem cv1 = new ClienteViagem();
        cv1.setViagem(viagem1);
        cv1.setCliente(c1);
        cv1.setAvaliacaoViagem(6.7);

        ClienteViagem cv2 = new ClienteViagem();
        cv2.setViagem(viagem1);
        cv2.setCliente(c2);
        cv2.setAvaliacaoViagem(7.5);

        ClienteViagem cv3 = new ClienteViagem();
        cv3.setViagem(viagem2);
        cv3.setCliente(c2);
        cv3.setAvaliacaoViagem(8.5);

        ClienteViagem cv4 = new ClienteViagem();
        cv4.setViagem(viagem3);
        cv4.setCliente(c1);
        cv4.setAvaliacaoViagem(6.4);

        ClienteViagem cv5 = new ClienteViagem();
        cv5.setViagem(viagem4);
        cv5.setCliente(c3);
        cv5.setAvaliacaoViagem(6.5);



        ArrayList<ClienteViagem> listaCV1 = new ArrayList<>();
        listaCV1.add(cv1);
        listaCV1.add(cv2);

        ArrayList<ClienteViagem> listaCV2 = new ArrayList<>();
        listaCV2.add(cv3);

        ArrayList<ClienteViagem> listaCV3 = new ArrayList<>();
        listaCV3.add(cv4);

        ArrayList<ClienteViagem> listaCV4 = new ArrayList<>();
        listaCV4.add(cv5);



        ArrayList<ClienteViagem> listaCV11 = new ArrayList<>();
        listaCV11.add(cv1);
        listaCV11.add(cv4);

        ArrayList<ClienteViagem> listaCV12 = new ArrayList<>();
        listaCV12.add(cv2);
        listaCV12.add(cv3);

        ArrayList<ClienteViagem> listaCV13 = new ArrayList<>();
        listaCV13.add(cv5);


        viagem1.setClienteViagem(listaCV1);
        viagem2.setClienteViagem(listaCV2);
        viagem3.setClienteViagem(listaCV3);
        viagem4.setClienteViagem(listaCV4);

        c1.setClienteViagem(listaCV11);
        c2.setClienteViagem(listaCV12);
        c3.setClienteViagem(listaCV13);



        ArrayList<Viagem> listaViagem1 = new ArrayList<>();
        listaViagem1.add(viagem1);
        listaViagem1.add(viagem2);

        ArrayList<Viagem> listaViagem2 = new ArrayList<>();
        listaViagem2.add(viagem3);
        listaViagem2.add(viagem4);

        agencia1.setViagens(listaViagem1);
        agencia2.setViagens(listaViagem2);


        clienteRepository.save(c1);
        clienteRepository.save(c2);
        clienteRepository.save(c3);
        viagemRepository.save(viagem1);
        viagemRepository.save(viagem2);
        viagemRepository.save(viagem3);
        viagemRepository.save(viagem4);
        clienteViagemRepository.save(cv1);
        clienteViagemRepository.save(cv2);
        clienteViagemRepository.save(cv3);
        clienteViagemRepository.save(cv4);
        clienteViagemRepository.save(cv5);
        agenciaRepository.save(agencia1);
        agenciaRepository.save(agencia2);
        */
    }
}
