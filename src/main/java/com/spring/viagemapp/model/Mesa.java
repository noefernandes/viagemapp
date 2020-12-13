package com.spring.viagemapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.lang.Nullable;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="mesa")
public class Mesa extends Servico{

    private int numero;
    private String data;
    private String inicioReserva;
    private boolean ocupada;
    private String estado;
    private int totalCompras;

    
    @JsonBackReference("restaurante_mesa-mesa")
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "restaurante_id")
    private PrestadorDeServico restaurante;

    //private int qtdPassageiros;

    //@JsonManagedReference("cliente_viagem-viagem")
    //@OneToMany(mappedBy = "viagem")
    //private List<ClienteViagem> clienteViagem;

    private long idRestaurante;

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getInicioReserva() {
		return inicioReserva;
	}

	public void setInicioReserva(String inicioReserva) {
		this.inicioReserva = inicioReserva;
	}

	public boolean isOcupada() {
		return ocupada;
	}
	

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public void setOcupada(boolean ocupada) {
		this.ocupada = ocupada;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public PrestadorDeServico getRestaurante() {
		return restaurante;
	}

	public void setRestaurante(PrestadorDeServico restaurante) {
		this.restaurante = restaurante;
	}

	public long getIdRestaurante() {
		return idRestaurante;
	}

	public void setIdRestaurante(long idRestaurante) {
		this.idRestaurante = idRestaurante;
	}

	public int getTotalCompras() {
		return totalCompras;
	}

	public void setTotalCompras(int totalCompras) {
		this.totalCompras = totalCompras;
	}
	
}