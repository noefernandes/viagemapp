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
public class Viagem extends Servico{
    
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
    
    @JsonBackReference("agencia_viagem-viagem")
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "agencia_id")
    private PrestadorDeServico agencia;

    //private int qtdPassageiros;

    //@JsonManagedReference("cliente_viagem-viagem")
    //@OneToMany(mappedBy = "viagem")
    //private List<ClienteViagem> clienteViagem;

    private long idAgencia;
    
    public long getIdAgencia() {
        return idAgencia;
    }

    public void setIdAgencia(long idAgencia) {
        this.idAgencia = idAgencia;
    }
    
    //private ClienteViagem clienteViagem;

    public PrestadorDeServico getAgencia() {
        return agencia;
    }

    public void setAgencia(PrestadorDeServico agencia) {
        this.agencia = agencia;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
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

    public int getQtdPassageiros() {
        return qtdPassageiros;
    }

    public void setQtdPassageiros(int qtdPassageiros) {
        this.qtdPassageiros = qtdPassageiros;
    }

    //public List<ClienteViagem> getClienteViagem() {
    //    return clienteViagem;
    //}

    //public void setClienteViagem(List<ClienteViagem> clienteViagem) {
    //    this.clienteViagem = clienteViagem;
    //}
}
