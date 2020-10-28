package com.spring.viagemapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.lang.Nullable;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="viagem")
public class Viagem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_viagem")
    @SequenceGenerator(name="seq_viagem", initialValue=1, allocationSize=1)
    private Long idv;
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

    @ElementCollection
    private List<String> tags;

    private double preco;

    //private int qtdPassageiros;
    private int capacidade;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "agencia_id")
    private Agencia agencia;

    @JsonManagedReference("cliente_viagem-viagem")
    @OneToMany(mappedBy = "viagem", cascade = CascadeType.ALL)
    private List<ClienteViagem> clienteViagem;

    private long idAgencia;

    //private ClienteViagem clienteViagem;


    public Agencia getAgencia() {
        return agencia;
    }

    public void setAgencia(Agencia agencia) {
        this.agencia = agencia;
    }



    public Long getIdv() {
        return idv;
    }

    public void setIdv(Long idv) {
        this.idv = idv;
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

    public long getIdAgencia() {
        return idAgencia;
    }

    public void setIdAgencia(long idAgencia) {
        this.idAgencia = idAgencia;
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

    /*public int getQtdPassageiros() {
        return qtdPassageiros;
    }

    public void setQtdPassageiros(int qtdPassageiros) {
        this.qtdPassageiros = qtdPassageiros;
    }*/

    public List<ClienteViagem> getClienteViagem() {
        return clienteViagem;
    }

    public void setClienteViagem(List<ClienteViagem> clienteViagem) {
        this.clienteViagem = clienteViagem;
    }
}
