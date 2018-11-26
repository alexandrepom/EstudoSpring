package br.com.gonzaga.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gonzaga.domain.Categoria;
import br.com.gonzaga.repositories.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired //injeta o objeto 
	private CategoriaRepository repo;
	
	/**
	 * Metodo busca categoria por ID.Retorna null caso o id passado não exista nenhuma categoria vinculada.
	 * @param {Integer} ID do produto.
	 * @return {Categoria | null} Retorna a categoria do ID ou null caso não exista o ID informado 
	 */
	 
	public Categoria buscar(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElse(null);
	}
	
	public void salvar(Categoria categoria) {
		
	}

}
