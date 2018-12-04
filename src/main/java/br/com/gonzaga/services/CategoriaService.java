package br.com.gonzaga.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.gonzaga.domain.Categoria;
import br.com.gonzaga.dto.CategoriaDTO;
import br.com.gonzaga.repositories.CategoriaRepository;
import br.com.gonzaga.services.exception.DataIntegrityException;
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
	 
	public Categoria find(Integer id) throws ObjectNotFoundException {
		Optional<Categoria> obj = repo.findById(id);
		
		return obj.orElseThrow(
			()-> new ObjectNotFoundException("Objeto não encontrado. ID: " + id + ", Tipo: "+ Categoria.class.getName()));
	}
	
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);		
	}

	public Categoria update(Categoria obj) {
		Categoria newObj = find(obj.getId());
		obj.setNome(newObj.getNome());;
		return repo.save(obj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Operação não permitida. A categoria possui produtos associados a ela.");
		}
	}

	public List<Categoria> findAll() {
		
		return repo.findAll();
	}
	
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		//PageRequest pageReq = new PageRequ
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	
	public Categoria fromDTO(CategoriaDTO objDto) {
		return new Categoria(objDto.getId(), objDto.getNome());
	}

}
