package com.spring.viagemapp.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="cliente_viagem")
public class ClienteViagem {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cliente_viagem")
	@SequenceGenerator(name="seq_cliente_viagem", initialValue=1, allocationSize=1)
	private long id;


	private long idViagem;
	private long idCliente;

	@JsonBackReference("cliente-cliente_viagem")
	@ManyToOne(cascade={CascadeType.ALL})
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;

	@JsonBackReference("cliente_viagem-viagem")
	@ManyToOne(cascade={CascadeType.ALL})
	@JoinColumn(name = "viagem_id")
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





	public long getIdViagem() {
		return idViagem;
	}

	public void setIdViagem(long idViagem) {
		this.idViagem = idViagem;
	}

	public long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(long idCliente) {
		this.idCliente = idCliente;
	}

}
