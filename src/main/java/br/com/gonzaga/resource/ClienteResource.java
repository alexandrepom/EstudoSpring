package br.com.gonzaga.resource;

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

import br.com.gonzaga.domain.Cliente;
import br.com.gonzaga.dto.ClienteDTO;
import br.com.gonzaga.services.ClienteService;

@RestController //indica que a classe é um controlador REST
@RequestMapping(value="/clientes") //indica o endpoint
public class ClienteResource {

	@Autowired
	ClienteService service;
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public ResponseEntity<Cliente> find(@PathVariable Integer id){
		Cliente cliente = service.find(id);
		return ResponseEntity.ok().body(cliente);
	}
	
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objDto, @PathVariable Integer id){
		Cliente obj = service.fromDTO(objDto);
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
	public ResponseEntity<List<ClienteDTO>> find(){
		List<Cliente> list = service.findAll();
		List<ClienteDTO> categorias = list.stream().map( obj-> new ClienteDTO(obj)).collect(Collectors.toList()	);

		return ResponseEntity.ok().body(categorias);
	}
	
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> findPage(
			@RequestParam(value="page",defaultValue="0") Integer page,
			@RequestParam(value="linesPerPage",defaultValue="24") Integer linePerPages,
			@RequestParam(value="orderBy",defaultValue="nome") String orderBy, 
			@RequestParam(value="direction",defaultValue="ASC") String direction){
		
		Page<Cliente> list = service.findPage(page, linePerPages, orderBy, direction);
		Page<ClienteDTO> categorias = list.map( obj-> new ClienteDTO(obj));

		return ResponseEntity.ok().body(categorias);
	}

	
}
