package br.com.gonzaga.repositories;
/**
 * Interface Repositório (DAO) para acesso aos dados da Categoria com base no identificador 
 */
 

/*
 * Interface que serve de DAO. Deve extender JpaRepository<Classe, ID>.
 * Classe = Tipo de dado que será retornado e ID é o identificador único da classe. Ou seja, o ID
 * */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gonzaga.domain.Categoria;

@Repository //informa ao spring que trata-se de um repositorio (DAO).
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

}
