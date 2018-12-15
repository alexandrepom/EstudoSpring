/**
 * Classe utilizada para controlar as chamadas REST ao endpoint "/categorias".
 * Ela está vinculada ao domínio Categorias.
 *   
 * @author Alexandre Gonzaga
 * @version 1.0
 * 
 * 
 * */

package br.com.gonzaga.resource;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.gonzaga.domain.Categoria;
import br.com.gonzaga.dto.CategoriaDTO;
import br.com.gonzaga.services.CategoriaService;
import javassist.tools.rmi.ObjectNotFoundException;

@RestController //controller especializado para resposta REST
@RequestMapping(value="/categorias") //endpoint "categorias" da API REST
public class CategoriaResource {
	
	@Autowired
	private CategoriaService service;
	
	/**
	 * Responde as requests GET HTTP devolvendo a categoria.
	 * @return {ResponseEntity<?>} 
	 * @throws ObjectNotFoundException 
	 * */
	/* A anotação @PathVariable vincula o parametro passado na url ao parametro do método. 
	 * O tipo ResponseEntity<?> é um objeto especial do Spring que encapsula uma resposta
	 * REST. a ? é usada para deixar o retorno de qualquer tipo pois não se sabe se o tipo
	 * buscado será encontrado ou não*/
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Categoria> find(@PathVariable Integer id){
		Categoria obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}
	
	//Responde o Método POST na URL /categorias inserindo o json enviado no corpo da requisição 
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO objDto){
		
		Categoria obj = service.fromDTO(objDto);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().
				path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody CategoriaDTO objDto, @PathVariable Integer id){
		Categoria obj = service.fromDTO(objDto);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> find(){
		List<Categoria> list = service.findAll();
		List<CategoriaDTO> categorias = list.stream().map( obj-> new CategoriaDTO(obj)).collect(Collectors.toList()	);

		return ResponseEntity.ok().body(categorias);
	}
	
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<CategoriaDTO>> findPage(
			@RequestParam(value="page",defaultValue="0") Integer page,
			@RequestParam(value="linesPerPage",defaultValue="24") Integer linePerPages,
			@RequestParam(value="orderBy",defaultValue="nome") String orderBy, 
			@RequestParam(value="direction",defaultValue="ASC") String direction){
		
		Page<Categoria> list = service.findPage(page, linePerPages, orderBy, direction);
		Page<CategoriaDTO> categorias = list.map( obj-> new CategoriaDTO(obj));

		return ResponseEntity.ok().body(categorias);
	}
	
}
