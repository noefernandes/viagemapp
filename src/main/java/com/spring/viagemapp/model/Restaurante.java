package com.spring.viagemapp.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.spring.viagemapp.model.Usuario;

@Entity
@Table(name="restaurante")
public class Restaurante extends PrestadorDeServico{
    //@NotBlank
    //private String nome;
    //@NotBlank
    //private String email;
    //@NotBlank
    //private String cnpj;
    
    //private double nota = 0;


    @JsonManagedReference("restaurante_mesa-mesa")
    @OneToMany(mappedBy = "restaurante")
    private List<Mesa> mesas;
    
    @JsonManagedReference("Avaliacao-restaurante-reference")
    @OneToMany(mappedBy = "restaurante")
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

    public List<Mesa> getMesas() {
        return mesas;
    }

    public void addMesa(Mesa mesa){mesas.add(mesa);}

    public void setMesas(List<Mesa> mesas) {
        this.mesas = mesas;
    }
}