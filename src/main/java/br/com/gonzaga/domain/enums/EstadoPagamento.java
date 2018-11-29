package br.com.gonzaga.domain.enums;

public enum EstadoPagamento {
	PENDENTE(1,"Pendente"),
	QUITADO(2, "Quitado"),
	CANCELADO(3, "Cancelado");
	
	private int cod;
	private String descricao;
	
	private EstadoPagamento(int codigo, String descricao) {
		this.cod=codigo;
		this.descricao=descricao;
	}
	
	public int getCod() {
		return this.cod;
	}
	
	public String getDescricao() {
		return this.descricao;
	}
	
	public static EstadoPagamento toEnum(Integer codigo) {
		if(codigo == null) return null;
		for(EstadoPagamento x : EstadoPagamento.values()) {
			if(codigo.equals(x.getCod())) {
				return x;
			}
		}
		throw new IllegalArgumentException("Argumento da Enumeração EstadoPagamento Inválido");
	}

}
