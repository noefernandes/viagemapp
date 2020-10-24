package com.spring.viagemapp.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="avaliacaoperuser")
public class AvaliacaoPerUser 
{
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_avaliacao")
    @SequenceGenerator(name="seq_avaliacao", initialValue=1, allocationSize=1)
    private long id;
	
	@ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_agencia",insertable=false, updatable=false)
    private Agencia agencia;
	
	@ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_cliente",insertable=false, updatable=false)
    private Cliente cliente;

    private double avaliacaoConforto;
   
    private double avaliacaoPreco;
    
    private double avaliacaoAtendimento;
    
    private double avaliacaoRapidez;
    
    private double avaliacaoLimpeza;
    
    private String comentarios;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public double getAvaliacaoConforto() {
		return avaliacaoConforto;
	}

	public void setAvaliacaoConforto(double avaliacaoConforto) {
		this.avaliacaoConforto = avaliacaoConforto;
	}

	public double getAvaliacaoPreco() {
		return avaliacaoPreco;
	}

	public void setAvaliacaoPreco(double avaliacaoPreco) {
		this.avaliacaoPreco = avaliacaoPreco;
	}

	public double getAvaliacaoAtendimento() {
		return avaliacaoAtendimento;
	}

	public void setAvaliacaoAtendimento(double avaliacaoAtendimento) {
		this.avaliacaoAtendimento = avaliacaoAtendimento;
	}

	public double getAvaliacaoRapidez() {
		return avaliacaoRapidez;
	}

	public void setAvaliacaoRapidez(double avaliacaoRapidez) {
		this.avaliacaoRapidez = avaliacaoRapidez;
	}

	public double getAvaliacaoLimpeza() {
		return avaliacaoLimpeza;
	}

	public void setAvaliacaoLimpeza(double avaliacaoLimpeza) {
		this.avaliacaoLimpeza = avaliacaoLimpeza;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}
}
