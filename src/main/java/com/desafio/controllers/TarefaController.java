package com.desafio.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.model.Pessoa;
import com.desafio.model.Tarefa;
import com.desafio.service.TarefaService;
import com.desafio.view.PessoaDTO;
import com.desafio.view.TarefaDTO;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

	@Autowired
    private TarefaService tarefaService;

	@PostMapping
	@Transactional(rollbackFor = Exception.class)
	public TarefaDTO salvarTarefa(@RequestBody Tarefa tarefa, HttpServletRequest request) throws IOException {
		TarefaDTO tarefaDTO = tarefaService.salvarTarefa(tarefa);
		
		return tarefaDTO;
	}
	
    @PutMapping("/alocar/{tarefaId}/{pessoaId}")
    public TarefaDTO alocarPessoaNaTarefa(
        @PathVariable Long tarefaId,
        @PathVariable Long pessoaId) {
        TarefaDTO tarefaDTO = tarefaService.alocarPessoaNaTarefa(tarefaId, pessoaId);
        return tarefaDTO;
    }
    
    @PutMapping("/finalizar/{tarefaId}")
    public TarefaDTO finalizarTarefa(@PathVariable Long tarefaId) {
        TarefaDTO tarefaDTO = tarefaService.finalizarTarefa(tarefaId);
        return tarefaDTO;
    }
	
    @DeleteMapping("/delete/tarefas/{id}")
	@Transactional(rollbackFor = Exception.class)
	public TarefaDTO removerTarefa(@PathVariable Long id, HttpServletRequest request) throws IOException {
		TarefaDTO tarefaDTO = tarefaService.removerTarefa(id);
		return tarefaDTO;
	}
    
    @GetMapping("/pendentes")
    public List<TarefaDTO> listarTarefasPendentes() {
        return tarefaService.listarTarefasPendentes();
    }
}
