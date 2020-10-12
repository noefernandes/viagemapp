package com.spring.viagemapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="viagem")
public class Viagem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_viagem")
    @SequenceGenerator(name="seq_viagem", initialValue=1, allocationSize=1)
    private Long id;
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

    @NotBlank
    private List<String> tags;

    private double preco;
    private int capacidade;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "agencia_id")
    private Agencia agencia;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public List<String> getTags(){ return tags;}

    public void setTags(List<String> tags){ this.tags = tags; }

    public void addTags(List<String> tags){ this.tags.addAll((tags)); }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public Agencia getAgencia() {
        return agencia;
    }

    public void setAgencia(Agencia agencia) {
        this.agencia = agencia;
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
}
