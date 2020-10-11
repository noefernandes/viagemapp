package com.spring.viagemapp.model;

import javax.persistence.*;

@Entity
@Table(name="preferencias_usuario")
public class Preferencias 
{
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_pref_usuario")
    @SequenceGenerator(name="seq_pref_usuario", initialValue=1, allocationSize=1)
	private long id;
	
	@OneToOne(mappedBy="cliente", cascade = CascadeType.ALL)
	private long id_cliende;
	
	@OneToMany(mappedBy="preferencias_usuario", cascade = CascadeType.ALL)
	private long id_tag;

	public long getId() 
	{
		return id;
	}

	public void setId(long id) 
	{
		this.id = id;
	}

	public long getId_cliende() 
	{
		return id_cliende;
	}

	public void setId_cliende(long id_cliende) 
	{
		this.id_cliende = id_cliende;
	}

	public long getId_tag() 
	{
		return id_tag;
	}

	public void setId_tag(long id_tag) 
	{
		this.id_tag = id_tag;
	}
	
	
}
