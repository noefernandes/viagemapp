package com.spring.viagemapp.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.spring.viagemapp.model.Usuario;

@Entity
@Table(name="hotel")
public class Hotel extends PrestadorDeServico{
    //@NotBlank
    //private String nome;
    //@NotBlank
    //private String email;
    //@NotBlank
    //private String cnpj;
    
    //private double nota = 0;


    @JsonManagedReference("hotel_quarto-quarto")
    @OneToMany(mappedBy = "hotel")
    private List<Quarto> quartos;
    
    @JsonManagedReference("Avaliacao-hotel-reference")
    @OneToMany(mappedBy = "hotel")
    private List<AvaliacaoPerUser> avaliacoes;


    //public void setNota(double nota){ this.nota = nota;}
    //public double getNota(){return nota;}
    
    public List<AvaliacaoPerUser> getAvaliacoes() {
		return avaliacoes;
	}

	public void setAvaliacoes(List<AvaliacaoPerUser> avaliacoes) {
		this.avaliacoes = avaliacoes;
	}

	//public String getNome() {
    //    return nome;
    //}

    //public String getEmail() { return email; }

    //public void setEmail(String email) { this.email = email; }

    //public void setNome(String nome) {
    //    this.nome = nome;
    //}

    //public String getCnpj() {
    //    return cnpj;
    //}

    //public void setCnpj(String cnpj) {
    //    this.cnpj = cnpj;
    //}

    public List<Quarto> getQuartos() {
        return quartos;
    }

    public void addQuarto(Quarto quarto){quartos.add(quarto);}

    public void setQuartos(List<Quarto> quartos) {
        this.quartos = quartos;
    }
}