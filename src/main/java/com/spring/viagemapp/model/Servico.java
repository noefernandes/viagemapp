package com.spring.viagemapp.model;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Inheritance
public abstract class Servico 
{
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_servico")
    @SequenceGenerator(name="seq_servico", initialValue=1, allocationSize=1)
    private Long id;
	
	private double preco;
	
	private int capacidade;
	
	@ElementCollection
    private List<String> tags;
	
	@JsonManagedReference("cliente_quarto-quarto")
    @OneToMany(mappedBy = "quarto")
    private List<ClienteQuarto> clienteQuarto;
	
	public List<ClienteQuarto> getClienteQuarto() {
        return clienteQuarto;
    }

    public void setClienteViagem(List<ClienteQuarto> clienteQuarto) {
        this.clienteQuarto= clienteQuarto;
    }
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public List<String> getTags(){ return tags;}

    public void setTags(List<String> tags){ this.tags = tags; }

    public void addTags(List<String> tags){ this.tags.addAll((tags)); }
    
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
}
