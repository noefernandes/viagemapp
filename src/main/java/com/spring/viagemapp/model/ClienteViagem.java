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

	private double avaliacaoViagem;

	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name = "id_cliente")
	private Cliente cliente;

	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name = "id_viagem")
	private Viagem viagem;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Viagem getViagem() {
		return viagem;
	}

	public void setViagem(Viagem viagem) {
		this.viagem = viagem;
	}

	public double getAvaliacaoViagem() {
		return avaliacaoViagem;
	}

	public void setAvaliacaoViagem(double avaliacaoViagem) {
		this.avaliacaoViagem = avaliacaoViagem;
	}
}
