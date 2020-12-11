package com.spring.viagemapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Calendar;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="quarto")
public class Quarto extends Servico{
    
    @NotBlank
    private int numero;
    @NotBlank
    private int andar;
    @NotBlank
    private String inicioReserva;
    @NotBlank
    private String fimReserva;
 
    
    @JsonBackReference("hotel_quarto-quarto")
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "hotel_id")
    private PrestadorDeServico hotel;

    //private int qtdPassageiros;

    //@JsonManagedReference("cliente_viagem-viagem")
    //@OneToMany(mappedBy = "viagem")
    //private List<ClienteViagem> clienteViagem;

    private long idHotel;
    
    public long getIdHotel() {
        return idHotel;
    }

    public void setIdHotel(long idHotel) {
        this.idHotel = idHotel;
    }
    
    //private ClienteViagem clienteViagem;

    public PrestadorDeServico getHotel() {
        return hotel;
    }

    public void setHotel(PrestadorDeServico hotel) {
        this.hotel = hotel;
    }


    public void setInicioReserva(String inicioReserva) {
        this.inicioReserva = inicioReserva;
    }

    public String getInicioReserva() {
        return inicioReserva;
    }

    public void setFimReserva(String fimReserva) {
        this.fimReserva = fimReserva;
    }

    public String getFimReserva() {
        return fimReserva;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
     
    public int getAndar() {
        return andar;
    }

    public void setAndar(int andar) {
        this.andar = andar;
    }

    //public List<ClienteViagem> getClienteViagem() {
    //    return clienteViagem;
    //}

    //public void setClienteViagem(List<ClienteViagem> clienteViagem) {
    //    this.clienteViagem = clienteViagem;
    //}
}
