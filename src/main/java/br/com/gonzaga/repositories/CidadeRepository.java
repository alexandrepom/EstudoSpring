package br.com.gonzaga.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.gonzaga.domain.Cidade;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {
	
	@Transactional(readOnly=true)
	@Query("SELECT obj FROM Cidade obj WHERE obj.estado.id= :estado_Id ORDER BY obj.nome")
	public List<Cidade> findCidades(@Param("estado_Id")Integer estadoId);

}
