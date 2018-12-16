/**
 * Classe utilizada para controlar as chamadas REST ao endpoint "/categorias".
 * Ela está vinculada ao domínio Produtos.
 *   
 * @author Alexandre Gonzaga
 * @version 1.0
 * 
 * 
 * */

package br.com.gonzaga.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.gonzaga.domain.Produto;
import br.com.gonzaga.dto.ProdutoDTO;
import br.com.gonzaga.resource.utils.URL;
import br.com.gonzaga.services.ProdutoService;
import javassist.tools.rmi.ObjectNotFoundException;

@RestController //controller especializado para resposta REST
@RequestMapping(value="/produtos") //endpoint "categorias" da API REST
public class ProdutoResource {
	
	@Autowired
	private ProdutoService service;
	
	/**
	 * Responde as requests GET HTTP devolvendo os pedidos.
	 * @return {ResponseEntity<?>} 
	 * @throws ObjectNotFoundException 
	 * */
	/* A anotação @PathVariable vincula o parametro passado na url ao parametro do método. 
	 * O tipo ResponseEntity<?> é um objeto especial do Spring que encapsula uma resposta
	 * REST. a ? é usada para deixar o retorno de qualquer tipo pois não se sabe se o tipo
	 * buscado será encontrado ou não*/
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Produto> find(@PathVariable Integer id){
		Produto obj = service.buscar(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			@RequestParam(value="nome",defaultValue="0") String nome,
			@RequestParam(value="categorias",defaultValue="") String categorias,
			@RequestParam(value="page",defaultValue="0") Integer page,
			@RequestParam(value="linesPerPage",defaultValue="24") Integer linePerPages,
			@RequestParam(value="orderBy",defaultValue="nome") String orderBy, 
			@RequestParam(value="direction",defaultValue="ASC") String direction){
		
		List<Integer> ids = URL.decodeIntList(categorias);
		nome = URL.decodeParam(nome);
		
		Page<Produto> list = service.search(nome, ids, page, linePerPages, orderBy, direction);
		Page<ProdutoDTO> produtos = list.map( obj-> new ProdutoDTO(obj));

		return ResponseEntity.ok().body(produtos);
	}
}
