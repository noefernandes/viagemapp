package com.spring.viagemapp.model;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

@MappedSuperclass
public abstract class AvaliacaoPrestadorDeServico 
{
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_avaliacao")
    @SequenceGenerator(name="seq_avaliacao", initialValue=1, allocationSize=1)
    protected long id;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public abstract boolean avaliarPrestador(PrestadorDeServico prestador, Cliente cliente);
	public abstract List<Double> obterNotas();
}
