package br.com.gonzaga.resource;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.gonzaga.domain.Cidade;
import br.com.gonzaga.domain.Estado;
import br.com.gonzaga.dto.CidadeDTO;
import br.com.gonzaga.dto.EstadoDTO;
import br.com.gonzaga.services.CidadeService;
import br.com.gonzaga.services.EstadoService;

@RestController //indica que a classe é um controlador REST
@RequestMapping(value="/estados") //indica o endpoint
public class EstadoResource {
	
	@Autowired
	private EstadoService estadoService;
	@Autowired
	private CidadeService cidadeService;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<EstadoDTO>> findAll(){
		List<Estado> list = estadoService.findAll();
		List<EstadoDTO> listDto = list.stream().map(x-> new EstadoDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}
	
	
	@RequestMapping(value="/{estadoId}/cidades",method=RequestMethod.GET)
	public ResponseEntity<List<CidadeDTO>> find(@PathVariable Integer estadoId){
		List<Cidade> list = cidadeService.findByEstado(estadoId);
		List<CidadeDTO> listDto = list.stream().map(x-> new CidadeDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}


}
