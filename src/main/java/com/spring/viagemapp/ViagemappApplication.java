package com.spring.viagemapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

//------------------------ Lembrar de tirar isso depois (vai servir para o login) ---------------------//
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class ViagemappApplication {

	public static void main(String[] args) {

		/*Recomendador recomendador = Recomendador.getInstance();
		System.out.println("Retorno: -------------------");
		recomendador.recomendar("avaliacoes.txt");
		*/
		SpringApplication.run(ViagemappApplication.class, args);
	}

}
