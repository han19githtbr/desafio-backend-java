package com.desafio.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desafio.model.Departamento;
import com.desafio.model.Pessoa;
import com.desafio.repository.DepartamentoRepository;
import com.desafio.view.DepartamentoDTO;
import com.desafio.view.PessoaDTO;

@Service
public class DepartamentoService {

	@Autowired
	private DepartamentoRepository departamentoRepository;

	public DepartamentoDTO salvarDepartamento(Departamento departamento) {
		DepartamentoDTO departamentoDTO = new DepartamentoDTO();
		if(departamento.getTitulo() == null) {
			departamentoDTO.setSuccess(Boolean.FALSE);
			departamentoDTO.setMensagem("O título do departamento não pode ser nulo");
			return departamentoDTO;
		}
	
		departamentoRepository.save(departamento);
		
		departamentoDTO.setMensagem(departamento.getTitulo() + " Foi salvo com sucesso");
		
		departamentoDTO.setSuccess(Boolean.TRUE);
		return departamentoDTO;
	
	}
	
	public List<DepartamentoDTO> listarDepartamentosComQuantidade() {
        List<Departamento> departamentos = departamentoRepository.findAll();
        return departamentos.stream()
            .map(this::converterParaDTO)
            .collect(Collectors.toList());
    }

    private DepartamentoDTO converterParaDTO(Departamento departamento) {
        DepartamentoDTO departamentoDTO = new DepartamentoDTO();
        departamentoDTO.setId(departamento.getId());
        departamentoDTO.setTitulo(departamento.getTitulo());
        departamentoDTO.setQuantidadePessoas(departamento.getPessoas().size());
        departamentoDTO.setQuantidadeTarefas(departamento.getTarefas().size());
        return departamentoDTO;
    }
}
