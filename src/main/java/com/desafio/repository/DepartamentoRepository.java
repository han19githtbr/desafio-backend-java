package com.desafio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.desafio.model.Departamento;
import com.desafio.model.Pessoa;

public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {
	

}
