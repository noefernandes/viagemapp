package com.spring.viagemapp.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@SequenceGenerator(name="seq_agencia", initialValue=1, allocationSize=100)
@Table(name="agencia")
public class Agencia {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_agencia")
    private Long id;
    @NotBlank
    private String nome;
    @NotBlank
    private String email;
    @NotBlank
    private String cnpj;
    private double nota;

    @OneToMany(cascade = {CascadeType.ALL})
    private List<Viagem> viagens;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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