
package br.com.gonzaga.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.gonzaga.domain.Cidade;
import br.com.gonzaga.domain.Cliente;
import br.com.gonzaga.domain.Endereco;
import br.com.gonzaga.domain.enums.TipoCliente;
import br.com.gonzaga.dto.ClienteDTO;
import br.com.gonzaga.dto.ClienteNewDTO;
import br.com.gonzaga.repositories.CidadeRepository;
import br.com.gonzaga.repositories.ClienteRepository;
import br.com.gonzaga.repositories.EnderecoRepository;
import br.com.gonzaga.services.exception.DataIntegrityException;
import br.com.gonzaga.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;
	@Autowired
	private CidadeRepository cityRepo;
	@Autowired
	private EnderecoRepository endRepo;
	
	public Cliente find(Integer id) throws ObjectNotFoundException{
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(()->new ObjectNotFoundException("Objeto não encontrado. ID: " + id + ", Tipo: "+ Cliente.class.getName()));
	}
	
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
		return repo.save(newObj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Operação não permitida. O Cliente possui outras relações associadas a ele.");
		}
	}

	public List<Cliente> findAll() {
		
		return repo.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		//PageRequest pageReq = new PageRequ
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	
	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(),objDto.getNome(), objDto.getEmail(),null,null) ;
	}
	
	public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente cli =  new Cliente(null,objDto.getNome(),objDto.getEmail(),objDto.getCpfOuCnpj(),TipoCliente.toEnum(objDto.getTipo()));
		Cidade ci = cityRepo.findById(objDto.getCidadeId()).orElseThrow(()->new ObjectNotFoundException("Objeto não encontrado. ID: " + objDto.getCidadeId() + ", Tipo: "+ Cidade.class.getName()));
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(),objDto.getComplemento(),objDto.getBairro(), objDto.getCep(), cli, ci);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		if(objDto.getTelefone2()!=null) cli.getTelefones().add(objDto.getTelefone2());
		if(objDto.getTelefone3()!=null) cli.getTelefones().add(objDto.getTelefone3());
		return cli;
	}

	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		endRepo.saveAll(obj.getEnderecos());
		return obj;		
	}
}
