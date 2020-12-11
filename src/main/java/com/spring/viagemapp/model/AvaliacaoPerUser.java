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
	private long idHotel;
	private long idCliente;
	
	// Elemento do mapeamento com a agência
	@ManyToOne
	@JsonBackReference("Avaliacao-hotel-reference")
    @JoinColumn(name = "hotel_id")
    private PrestadorDeServico hotel;
	
	// Elemento do mapeamento com o cliente
	@ManyToOne
	@JsonBackReference("Avaliacao-cliente-reference")
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    private double avaliacaoConforto;
   
    private double avaliacaoPreco;
    
    private double avaliacaoAtendimento;
    
    private double avaliacaLocalidade;
    
    private double avaliacaoLimpeza;
    
    private String comentarios;

    public boolean avaliarPrestador(PrestadorDeServico prestador, Cliente cliente) 
    {
    	 sethotel(prestador);
         setCliente(cliente);
         setidHotel(prestador.getId());
         setIdCliente(cliente.getId());
         
         return true;
    }
    
	public List<Double> obterNotas()
	{
		List<Double> lista = new ArrayList<Double>();
		
		lista.add(avaliacaoConforto);
		lista.add(avaliacaoPreco);
		lista.add(avaliacaoAtendimento);
		lista.add(avaliacaoLocalidade);
		lista.add(avaliacaoLimpeza);
		
		return lista;
	}
    
	public long getidHotel() {
		return idHotel;
	}

	public void setidHotel(long idHotel) {
		this.idHotel = idHotel;
	}

	public long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(long idCliente) {
		this.idCliente = idCliente;
	}

	public PrestadorDeServico gethotel() {
		return hotel;
	}

	public void sethotel(PrestadorDeServico hotel) {
		this.hotel = hotel;
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

	public double getAvaliacaoLocalidade() {
		return avaliacaoLocalidade;
	}

	public void setAvaliacaoLocalidade(double avaliacaoLocalidade) {
		this.avaliacaoLocalidade = avaliacaoLocalidade;
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
