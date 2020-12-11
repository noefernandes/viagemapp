package com.spring.quartoapp.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="cliente_quarto")
public class ClienteQuarto {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cliente_quarto")
	@SequenceGenerator(name="seq_cliente_quarto", initialValue=1, allocationSize=1)
	private long id;


	private long idQuarto;
	private long idCliente;

	@JsonBackReference("cliente-cliente_quarto")
	@ManyToOne(cascade={CascadeType.ALL})
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;

	@JsonBackReference("cliente_quarto-quarto")
	@ManyToOne(cascade={CascadeType.ALL})
	@JoinColumn(name = "quarto_id")
	private Servico quarto;

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

	public Servico getQuarto() {
		return quarto;
	}

	public void setQuarto(Quarto quarto) {
		this.quarto = quarto;
	}

	public long getIdQuarto() {
		return idQuarto;
	}

	public void setIdQuarto(long idQuarto) {
		this.idQuarto = idQuarto;
	}

	public long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(long idCliente) {
		this.idCliente = idCliente;
	}
}
