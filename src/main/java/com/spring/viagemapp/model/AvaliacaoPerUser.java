package com.spring.viagemapp.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="avaliacaoperuser")
public class AvaliacaoPerUser extends AvaliacaoPrestadorDeServico
{	
	private long idAgencia;
	private long idCliente;
	
	// Elemento do mapeamento com a agência
	@ManyToOne
	@JsonBackReference("Avaliacao-agencia-reference")
    @JoinColumn(name = "agencia_id")
    private PrestadorDeServico agencia;
	
	// Elemento do mapeamento com o cliente
	@ManyToOne
	@JsonBackReference("Avaliacao-cliente-reference")
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    private double avaliacaoConforto;
   
    private double avaliacaoPreco;
    
    private double avaliacaoAtendimento;
    
    private double avaliacaoRapidez;
    
    private double avaliacaoLimpeza;
    
    private String comentarios;

    public boolean avaliarPrestador(PrestadorDeServico prestador, Cliente cliente) 
    {
    	 setAgencia(prestador);
         setCliente(cliente);
         setIdAgencia(prestador.getId());
         setIdCliente(cliente.getId());
         
         return true;
    }
    
	public List<Double> obterNotas()
	{
		List<Double> lista = new ArrayList<Double>();
		
		lista.add(avaliacaoConforto);
		lista.add(avaliacaoPreco);
		lista.add(avaliacaoAtendimento);
		lista.add(avaliacaoRapidez);
		lista.add(avaliacaoLimpeza);
		
		return lista;
	}
    
	public long getIdAgencia() {
		return idAgencia;
	}

	public void setIdAgencia(long idAgencia) {
		this.idAgencia = idAgencia;
	}

	public long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(long idCliente) {
		this.idCliente = idCliente;
	}

	public PrestadorDeServico getAgencia() {
		return agencia;
	}

	public void setAgencia(PrestadorDeServico agencia) {
		this.agencia = agencia;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
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

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}
}
