package br.com.gonzaga.domain.enums;

public enum TipoCliente {

	PESSOA_FISICA(1,"Pessoa Física"),
	PESSOA_JURIDICA(2, "Pessoa Jurídica");
	
	private int cod;
	private String descricao;
	
	private TipoCliente(int codigo, String descricao) {
		this.cod=codigo;
		this.descricao=descricao;
	}
	
	public int getCod() {
		return this.cod;
	}
	
	public String getDescricao() {
		return this.descricao;
	}
	
	public static TipoCliente toEnum(Integer codigo) {
		if(codigo == null) return null;
		for(TipoCliente x : TipoCliente.values()) {
			if(codigo.equals(x.getCod())) {
				return x;
			}
		}
		throw new IllegalArgumentException("Argumento da Enumeração TipoCliente Inválido");
	}
	
}
