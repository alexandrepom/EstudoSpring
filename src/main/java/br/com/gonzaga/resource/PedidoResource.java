/**
 * Classe utilizada para controlar as chamadas REST ao endpoint "/categorias".
 * Ela está vinculada ao domínio Pedidos.
 *   
 * @author Alexandre Gonzaga
 * @version 1.0
 * 
 * 
 * */

package br.com.gonzaga.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.gonzaga.domain.Pedido;
import br.com.gonzaga.services.PedidoService;
import javassist.tools.rmi.ObjectNotFoundException;

@RestController //controller especializado para resposta REST
@RequestMapping(value="/pedidos") //endpoint "categorias" da API REST
public class PedidoResource {
	
	@Autowired
	private PedidoService service;
	
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
	public ResponseEntity<Pedido> find(@PathVariable Integer id){
		Pedido obj = service.buscar(id);
		return ResponseEntity.ok().body(obj);
		
	}
}
