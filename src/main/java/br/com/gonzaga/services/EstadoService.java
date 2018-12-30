package br.com.gonzaga.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gonzaga.domain.Estado;
import br.com.gonzaga.repositories.EstadoRepository;

@Service
public class EstadoService {
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	public List<Estado> findAll(){
		return estadoRepository.findAllByOrderByNome();
	}

}
