package com.desafio.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.desafio.model.Tarefa;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
	
	@Query("SELECT t FROM Tarefa t " +
		       "LEFT JOIN t.pessoa p " +
		       "WHERE p IS NULL " +
		       "ORDER BY t.prazo ASC")
	List<Tarefa> findTarefasSemAlocacao(Pageable pageable);

}
