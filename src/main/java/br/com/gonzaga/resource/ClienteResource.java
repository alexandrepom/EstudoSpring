package br.com.gonzaga.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.gonzaga.domain.Cliente;
import br.com.gonzaga.services.ClienteService;

@RestController //indica que a classe é um controlador REST
@RequestMapping(value="/clientes") //indica o endpoint
public class ClienteResource {

	@Autowired
	ClienteService clienteService;
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public ResponseEntity<Cliente> find(@PathVariable Integer id){
		Cliente cliente = clienteService.buscar(id);
		return ResponseEntity.ok().body(cliente);
	}
	
}
