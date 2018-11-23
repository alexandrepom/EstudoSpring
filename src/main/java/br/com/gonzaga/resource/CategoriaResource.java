package br.com.gonzaga.resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController //controller especializado para resposta REST
@RequestMapping(value="/categorias") //endpoint "categorias" da API REST
public class CategoriaResource {
	
	@RequestMapping(method=RequestMethod.GET) //atribui o método GET do HTTP à função listar()
	public String listar() {
		return "REST está funcionando";
	}
}
