package com.spring.viagemapp.model;

import java.util.List;

public class Avaliacoes {
    private double avaliacaoGeral;
    private double avaliacaoConforto;
    private double avaliacaoPreco;
    private double avaliacaoAtendimento;
    private double avaliacaoRapidez;
    private double avaliacaoLimpeza;

    private String comentarios;
    private String avaliador;

    public double getAvaliacaoGeral() {
        return avaliacaoGeral;
    }

    public void setComentarios(String comentarios){
        this.comentarios = comentarios;
    }

    public String getAvaliador() {
        return avaliador;
    }

    public void setAvaliador(String avaliador){
        this.avaliador = avaliador;
    }

    public String getComentarios(){
        return comentarios;
    }

    public void setAvaliacaoGeral(double avaliacaoGeral) {
        this.avaliacaoGeral = avaliacaoGeral;
    }

    public double getAvaliacaoConforto() {
        return avaliacaoConforto;
    }

    public void setAvaliacaoConforto(double avaliacaoConforto) {
        this.avaliacaoConforto = avaliacaoConforto;
    }

    public double getAvaliacaoPreco() {
        return avaliacaoPreco;
    }

    public void setAvaliacaoPreco(double avaliacaoPreco) {
        this.avaliacaoPreco = avaliacaoPreco;
    }

    public double getAvaliacaoAtendimento() {
        return avaliacaoAtendimento;
    }

    public void setAvaliacaoAtendimento(double avaliacaoAtendimento) {
        this.avaliacaoAtendimento = avaliacaoAtendimento;
    }

    public double getAvaliacaoRapidez() {
        return avaliacaoRapidez;
    }

    public void setAvaliacaoRapidez(double avaliacaoRapidez) {
        this.avaliacaoRapidez = avaliacaoRapidez;
    }

    public double getAvaliacaoLimpeza() {
        return avaliacaoLimpeza;
    }

    public void setAvaliacaoLimpeza(double avaliacaoLimpeza) {
        this.avaliacaoLimpeza = avaliacaoLimpeza;
    }
}
