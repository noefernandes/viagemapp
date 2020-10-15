package com.spring.viagemapp.model;

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
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<ClienteViagem> clienteViagem;

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

	public void setTags(List<String> tagsCliente) {
		this.tags = tagsCliente;
	}
	
	public void addTags(List<String> tagsCliente)
    {
    	this.tags.addAll((tagsCliente)); 
    }

	public List<ClienteViagem> getClienteViagem() {
        return clienteViagem;
    }

    public void setClienteViagem(List<ClienteViagem> clienteViagem) {
        this.clienteViagem = clienteViagem;
    }
    
}