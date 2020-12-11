package com.spring.viagemapp.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="cliente_mesa")
public class ClienteMesa {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cliente_mesa")
	@SequenceGenerator(name="seq_cliente_viagem", initialValue=1, allocationSize=1)
	private long id;


	private long idMesa;
	private long idCliente;

	@JsonBackReference("cliente-cliente_mesa")
	@ManyToOne(cascade={CascadeType.ALL})
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;

	@JsonBackReference("cliente_mesa-mesa")
	@ManyToOne(cascade={CascadeType.ALL})
	@JoinColumn(name = "mesa_id")
	private Servico mesa;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getIdMesa() {
		return idMesa;
	}

	public void setIdMesa(long idMesa) {
		this.idMesa = idMesa;
	}

	public long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(long idCliente) {
		this.idCliente = idCliente;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Servico getMesa() {
		return mesa;
	}

	public void setMesa(Servico mesa) {
		this.mesa = mesa;
	}

}
