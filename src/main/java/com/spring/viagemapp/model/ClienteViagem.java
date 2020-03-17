package com.spring.viagemapp.model;


import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="CLIENTEVIAGEM")
public class ClienteViagem {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@NotNull
	@OneToMany(mappedBy = "id")
	private List<Cliente> clientes;

	
	@NotNull
	@OneToMany(mappedBy = "id")
	private List<Viagem> viagens;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public List<Viagem> getViagens() {
		return viagens;
	}

	public void setViagens(List<Viagem> viagens) {
		this.viagens = viagens;
	}
	
	
}
