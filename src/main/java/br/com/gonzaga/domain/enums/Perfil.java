package br.com.gonzaga.domain.enums;

public enum Perfil {
	ADMIN(1,"ROLE_ADMIN"),
	CLIENTE(2, "ROLE_CLIENTE");
	
	private int cod;
	private String descricao;
	
	private Perfil(int codigo, String descricao) {
		this.cod=codigo;
		this.descricao=descricao;
	}
	
	public int getCod() {
		return this.cod;
	}
	
	public String getDescricao() {
		return this.descricao;
	}
	
	public static Perfil toEnum(Integer codigo) {
		if(codigo == null) return null;
		for(Perfil x : Perfil.values()) {
			if(codigo.equals(x.getCod())) {
				return x;
			}
		}
		throw new IllegalArgumentException("Argumento da Enumeração EstadoPagamento Inválido");
	}

}
