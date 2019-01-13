package br.com.gonzaga.resource;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.gonzaga.domain.Cliente;
import br.com.gonzaga.dto.ClienteDTO;
import br.com.gonzaga.dto.ClienteNewDTO;
import br.com.gonzaga.services.ClienteService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController //indica que a classe é um controlador REST
@RequestMapping(value="/clientes") //indica o endpoint
public class ClienteResource {

	@Autowired
	ClienteService service;
	
	@ApiOperation(value="busca o cliente por id")
	@ApiResponses({
		@ApiResponse(code=404, message="Código não existe")
		,@ApiResponse(code=400, message="Código não existe")//separar por vírgula as mensagens
	})
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public ResponseEntity<Cliente> find(@PathVariable Integer id){
		Cliente cliente = service.find(id);
		return ResponseEntity.ok().body(cliente);
	}

	@RequestMapping(value="/email",method=RequestMethod.GET)
	public ResponseEntity<Cliente> find(@RequestParam(value="value") String email){
		Cliente cliente = service.findByEmail(email);
		return ResponseEntity.ok().body(cliente);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objDto, @PathVariable Integer id){
		Cliente obj = service.fromDTO(objDto);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> find(){
		List<Cliente> list = service.findAll();
		List<ClienteDTO> categorias = list.stream().map( obj-> new ClienteDTO(obj)).collect(Collectors.toList()	);

		return ResponseEntity.ok().body(categorias);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
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
	
	//Responde o Método POST na URL /categorias inserindo o json enviado no corpo da requisição 
		@RequestMapping(method=RequestMethod.POST)
		public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO objDto){
			
			Cliente obj = service.fromDTO(objDto);
			obj = service.insert(obj);
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().
					path("/{id}").buildAndExpand(obj.getId()).toUri();
			return ResponseEntity.created(uri).build();
		}
		

	
}
