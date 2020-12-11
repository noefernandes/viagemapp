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
    
    @NotBlank
    private String localPartida;
    @NotBlank
    private String localChegada;
    @NotBlank
    private String horarioPartida;
    @NotBlank
    private String horarioChegada;

    @NotBlank
    private String data;
    private int qtdPassageiros;
    
    @JsonBackReference("restaurante_mesa-mesa")
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "restaurante_id")
    private PrestadorDeServico restaurante;

    //private int qtdPassageiros;

    //@JsonManagedReference("cliente_viagem-viagem")
    //@OneToMany(mappedBy = "viagem")
    //private List<ClienteViagem> clienteViagem;

    private long idRestaurante;

	public String getLocalPartida() {
		return localPartida;
	}

	public void setLocalPartida(String localPartida) {
		this.localPartida = localPartida;
	}

	public String getLocalChegada() {
		return localChegada;
	}

	public void setLocalChegada(String localChegada) {
		this.localChegada = localChegada;
	}

	public String getHorarioPartida() {
		return horarioPartida;
	}

	public void setHorarioPartida(String horarioPartida) {
		this.horarioPartida = horarioPartida;
	}

	public String getHorarioChegada() {
		return horarioChegada;
	}

	public void setHorarioChegada(String horarioChegada) {
		this.horarioChegada = horarioChegada;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public int getQtdPassageiros() {
		return qtdPassageiros;
	}

	public void setQtdPassageiros(int qtdPassageiros) {
		this.qtdPassageiros = qtdPassageiros;
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
}