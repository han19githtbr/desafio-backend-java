package com.desafio.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.model.Departamento;
import com.desafio.model.Pessoa;
import com.desafio.service.DepartamentoService;
import com.desafio.view.DepartamentoDTO;
import com.desafio.view.PessoaDTO;
import com.desafio.view.TarefaDTO;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/departamentos")
public class DepartamentoController {

	@Autowired
	private DepartamentoService departamentoService;

	@PostMapping
	@Transactional(rollbackFor = Exception.class)
	public DepartamentoDTO salvarDepartamento(@RequestBody Departamento departamento, HttpServletRequest request) throws IOException {
		DepartamentoDTO departamentoDTO = departamentoService.salvarDepartamento(departamento);
		
		return departamentoDTO;
	}
	
	@GetMapping
    public List<DepartamentoDTO> listarDepartamentosComQuantidade() {
        return departamentoService.listarDepartamentosComQuantidade();
    }
}
