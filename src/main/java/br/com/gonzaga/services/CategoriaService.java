package br.com.gonzaga.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gonzaga.domain.Categoria;
import br.com.gonzaga.repositories.CategoriaRepository;
import br.com.gonzaga.services.exception.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired //injeta o objeto 
	private CategoriaRepository repo;
	
	/**
	 * Metodo busca categoria por ID.Retorna null caso o id passado não exista nenhuma categoria vinculada.
	 * @param {Integer} ID do produto.
	 * @return {Categoria | null} Retorna a categoria do ID ou null caso não exista o ID informado 
	 * @throws ObjectNotFoundException 
	 */
	 
	public Categoria buscar(Integer id) throws ObjectNotFoundException {
		Optional<Categoria> obj = repo.findById(id);
		
		return obj.orElseThrow(
			()-> new ObjectNotFoundException("Objeto não encontrado. ID: " + id + ", Tipo: "+ Categoria.class.getName()));
	}
	
	public void salvar(Categoria categoria) {
		
	}

}
