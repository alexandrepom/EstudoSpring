package br.com.gonzaga.dto;

import java.io.Serializable;

import br.com.gonzaga.domain.Estado;

public class EstadoDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer Id;
	private String nome;
	
	
	
	public EstadoDTO() {
	}
	
	
	public EstadoDTO(Estado estado) {
		super();
		Id = estado.getId();
		this.nome = estado.getNome();
	}


	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	
}
