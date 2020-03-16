package com.spring.viagemapp.utils;

import com.spring.viagemapp.model.Cliente;
import com.spring.viagemapp.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class Auxiliar {

    @Autowired
    ClienteRepository clienteRepository;

    //Esta anotação sempre executa primeiro na aplicação.
    //@PostConstruct
    public void saveClientes(){
        List<Cliente> clientes = new ArrayList<>();
        Cliente c1 = new Cliente();
        c1.setNome("Sandro Pessoa");
        c1.setCpf("87965406");
        c1.setEmail("sandropessoa@fic.com");

        Cliente c2 = new Cliente();
        c2.setNome("Manoel Santana");
        c2.setCpf("93854092");
        c2.setEmail("manoelsantana@fic.com");

        clientes.add(c1);
        clientes.add(c2);

        for(Cliente cliente: clientes){
            Cliente clienteSalvo = clienteRepository.save(cliente);
            System.out.println(clienteSalvo.getId());
        }
    }
}
