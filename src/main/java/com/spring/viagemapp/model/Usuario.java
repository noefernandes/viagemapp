package com.spring.viagemapp.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;


@MappedSuperclass
public class Usuario{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_usuario")
    @SequenceGenerator(name="seq_usuario", initialValue=1, allocationSize=1)
    protected long id;
    @NotBlank
    protected String nomeUsuario;
    @NotBlank
    protected String senha;

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
