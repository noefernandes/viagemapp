package com.spring.viagemapp.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.util.ArrayList;
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

    @OneToMany(mappedBy = "agencia")
    private List<Viagem> viagens;

    @ElementCollection
    private List<Double> avaliacaoGeral;
    @ElementCollection
    private List<Double> avaliacaoConforto;
    @ElementCollection
    private List<Double> avaliacaoPreco;
    @ElementCollection
    private List<Double> avaliacaoAtendimento;
    @ElementCollection
    private List<Double> avaliacaoRapidez;
    @ElementCollection
    private List<Double> avaliacaoLimpeza;
    @ElementCollection
    private List<String> comentarios;
    //Nome dos usuarios que avaliaram
    @ElementCollection
    private List<String> avaliadores;

    public void addComentarios(String comentarios){
        this.comentarios.add(comentarios);
    }

    public List<String> getComentarios(){
        return comentarios;
    }

    public void addAvaliador(String avaliador){
        this.avaliadores.add(avaliador);
    }

    public List<String> getAvaliadores(){return avaliadores;}

    public Double getAvaliacaoGeral() {
        List<Double> avaliacao = new ArrayList<Double>();
        avaliacao.add(getAvaliacaoAtendimento());
        avaliacao.add(getAvaliacaoConforto());
        avaliacao.add(getAvaliacaoLimpeza());
        avaliacao.add(getAvaliacaoPreco());
        avaliacao.add(getAvaliacaoRapidez());

        Double sum = 0.0;

        for(int i = 0; i < avaliacao.size(); i++){
            sum += avaliacao.get(i);
        }

        sum /= avaliacao.size();
        return sum;
    }

    public Double getAvaliacaoConforto() {
        Double sum = 0.0;

        for(int i = 0; i < this.avaliacaoConforto.size(); i++){
            sum += this.avaliacaoConforto.get(i);
        }
        sum /= this.avaliacaoConforto.size();

        return sum;
    }

    public Double getAvaliacaoLimpeza() {
        Double sum = 0.0;

        for(int i = 0; i < this.avaliacaoLimpeza.size(); i++){
            sum += this.avaliacaoLimpeza.get(i);
        }

        sum /= this.avaliacaoLimpeza.size();
        return sum;
    }


    public Double getAvaliacaoPreco() {
        Double sum = 0.0;

        for(int i = 0; i < this.avaliacaoPreco.size(); i++){
            sum += this.avaliacaoPreco.get(i);
        }

        sum /= this.avaliacaoPreco.size();

        return sum;
    }

    public Double getAvaliacaoRapidez() {
        Double sum = 0.0;

        for(int i = 0; i < this.avaliacaoRapidez.size(); i++){
            sum += this.avaliacaoRapidez.get(i);
        }

        sum /= this.avaliacaoRapidez.size();

        return sum;
    }

    public Double getAvaliacaoAtendimento() {
        Double sum = 0.0;

        for(int i = 0; i < this.avaliacaoAtendimento.size(); i++){
            sum += this.avaliacaoAtendimento.get(i);
        }

        sum /= this.avaliacaoAtendimento.size();

        return sum;
    }

    public void addAvaliacaoAtendimento(Double avaliacao){
        this.avaliacaoAtendimento.add(avaliacao);
    }

    public void addAvaliacaoLimpeza(Double avaliacao){
        this.avaliacaoLimpeza.add(avaliacao);
    }

    public void addAvaliacaoPreco(Double avaliacao){
        this.avaliacaoPreco.add(avaliacao);
    }

    public void addAvaliacaoConforto(Double avaliacao){
        this.avaliacaoConforto.add(avaliacao);
    }

    public void addAvaliacaoRapidez(Double avaliacao){
        this.avaliacaoRapidez.add(avaliacao);
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

    public List<Viagem> getViagens() {
        return viagens;
    }

    public void addViagem(Viagem viagem){viagens.add(viagem);}

    public void setViagens(List<Viagem> viagens) {
        this.viagens = viagens;
    }
}