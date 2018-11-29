package br.com.gonzaga.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gonzaga.domain.Cliente;
import br.com.gonzaga.repositories.ClienteRepository;
import br.com.gonzaga.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente buscar(Integer id) throws ObjectNotFoundException{
		Optional<Cliente> obj = clienteRepository.findById(id);
		return obj.orElseThrow(()->new ObjectNotFoundException("Objeto não encontrado. ID: " + id + ", Tipo: "+ Cliente.class.getName()));
	}
	
}
