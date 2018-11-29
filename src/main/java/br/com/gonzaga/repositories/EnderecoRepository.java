package br.com.gonzaga.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gonzaga.domain.Endereco;
@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer>{

}
