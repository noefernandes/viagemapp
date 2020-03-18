package com.spring.viagemapp.model;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@SequenceGenerator(name="seq_viagem", initialValue=1, allocationSize=100)
@Table(name="viagem")
public class Viagem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_viagem")
    private Long id;
    @NotBlank
    private String localPartida;
    @NotBlank
    private String localChegada;
    private double preco;
    private double capacidade;
    private double avaliacao;

    @ManyToOne(cascade={CascadeType.ALL})
    private Agencia agencia;

    //Adding in the cascade = {CascadeType.ALL} on the Parent's reference to the Child
    // solved the problem in both cases. This saved the Child and the Parent.
    @OneToMany(mappedBy = "viagem", cascade = CascadeType.ALL)
    private List<ClienteViagem> clienteViagem;

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

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public double getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(double capacidade) {
        this.capacidade = capacidade;
    }

    public double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Agencia getAgencia() {
        return agencia;
    }

    public void setAgencia(Agencia agencia) {
        this.agencia = agencia;
    }

    public List<ClienteViagem> getClienteViagem() {
        return clienteViagem;
    }

    public void setClienteViagem(List<ClienteViagem> clienteViagem) {
        this.clienteViagem = clienteViagem;
    }
}
