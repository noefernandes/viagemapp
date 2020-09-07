package com.spring.viagemapp.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import com.spring.viagemapp.model.Usuario;

@Entity
@Table(name="agencia")
public class Agencia extends Usuario {
    @NotBlank
    private String nome;
    @NotBlank
    private String email;
    @NotBlank
    private String cnpj;
    private double nota;

    @OneToMany(mappedBy = "agencia")
    private List<Viagem> viagens;


    public String getNome() {
        return nome;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public List<Viagem> getViagens() {
        return viagens;
    }

    public void setViagens(List<Viagem> viagens) {
        this.viagens = viagens;
    }
}