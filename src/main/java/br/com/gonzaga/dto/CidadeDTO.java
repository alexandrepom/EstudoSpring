package br.com.gonzaga.dto;

import java.io.Serializable;

import br.com.gonzaga.domain.Cidade;

public class CidadeDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer Id;
	private String nome;
	
	
	
	public CidadeDTO() {
	}
	
	
	public CidadeDTO(Cidade cidade) {
		super();
		Id = cidade.getId();
		this.nome = cidade.getNome();
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
