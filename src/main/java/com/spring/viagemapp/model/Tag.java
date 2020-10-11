package com.spring.viagemapp.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

// Modela uma tabela de tags
// Essa tabela será utilizada para facilitar a busca por preferências de usuário
@Entity
@Table(name="tag")
public class Tag 
{
	// Mapeia o Id de cada tag
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tags")
    @SequenceGenerator(name="seq_tags", initialValue=1, allocationSize=1)
	private long id;
	// Nome da tag. Ex: TV
	@NotBlank
	private String opcao;
	
	public long getId() 
	{
		return id;
	}
	public void setId(long id) 
	{
		this.id = id;
	}
	public String getOpcao() 
	{
		return opcao;
	}
	public void setOpcao(String opcao) 
	{
		this.opcao = opcao;
	}
	
	
}
