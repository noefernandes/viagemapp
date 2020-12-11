package com.spring.viagemapp.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="cliente")
public class Cliente extends Usuario{
    @NotBlank
    private String nome;

    @NotBlank
    private String cpf;

    @NotBlank
    private String email;
    
    @ElementCollection
    private List<String> tags;

    //Adding in the cascade = {CascadeType.ALL} on the Parent's reference to the Child solved
    // the problem in both cases. This saved the Child and the Parent.
    @JsonManagedReference("cliente-cliente_mesa")
    @OneToMany(mappedBy = "cliente")
    private List<ClienteMesa> clienteMesa;
    
    @JsonManagedReference("Avaliacao-cliente-reference")
    @OneToMany(mappedBy = "cliente")
    private List<AvaliacaoPerUser> avaliacoes;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public List<ClienteMesa> getClienteMesa() {
		return clienteMesa;
	}

	public void setClienteMesa(List<ClienteMesa> clienteMesa) {
		this.clienteMesa = clienteMesa;
	}

	public List<AvaliacaoPerUser> getAvaliacoes() {
		return avaliacoes;
	}

	public void setAvaliacoes(List<AvaliacaoPerUser> avaliacoes) {
		this.avaliacoes = avaliacoes;
	}
	
	public void addTags(List<String> tagsCliente){
    	this.tags.addAll((tagsCliente)); 
    }
       
}