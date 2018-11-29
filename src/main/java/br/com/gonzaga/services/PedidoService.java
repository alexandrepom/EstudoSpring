package br.com.gonzaga.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gonzaga.domain.Pedido;
import br.com.gonzaga.repositories.PedidoRepository;
import br.com.gonzaga.services.exception.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	public Pedido buscar(Integer id) throws ObjectNotFoundException{
		Optional<Pedido> obj = pedidoRepository.findById(id);
		return obj.orElseThrow(()->new ObjectNotFoundException("Objeto não encontrado. ID: " + id + ", Tipo: "+ Pedido.class.getName()));
	}

}
