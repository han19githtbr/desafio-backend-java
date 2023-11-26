package com.desafio.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import com.desafio.model.Departamento;
import com.desafio.model.Pessoa;
import com.desafio.model.Tarefa;
import com.desafio.repository.PessoaRepository;
import com.desafio.repository.TarefaRepository;
import com.desafio.view.DepartamentoDTO;
import com.desafio.view.PessoaDTO;
import com.desafio.view.TarefaDTO;

@Service
public class TarefaService {

	@Autowired
    private PessoaRepository pessoaRepository;
	
	@Autowired
    private TarefaRepository tarefaRepository;

	public TarefaDTO salvarTarefa(Tarefa tarefa) {
		tarefa.setDataCriacao(LocalDateTime.now());
		TarefaDTO tarefaDTO = new TarefaDTO();
		if(tarefa.getTitulo() == null || tarefa.getDescricao() == null || tarefa.getPrazo() == null || tarefa.getDepartamento().getId() == null) {
			tarefaDTO.setSuccess(Boolean.FALSE);
			tarefaDTO.setMensagem("Os campos da tarefa não podem ser nulos");
			return tarefaDTO;
		}
	
		tarefaRepository.save(tarefa);
		
		tarefaDTO.setMensagem(tarefa.getTitulo() + " Foi salvo com sucesso");
		
		tarefaDTO.setSuccess(Boolean.TRUE);
		return tarefaDTO;
	
	}

	public TarefaDTO alocarPessoaNaTarefa(Long tarefaId, Long pessoaId) {
	    Tarefa tarefa = tarefaRepository.findById(tarefaId).orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada."));
	    Pessoa pessoa = pessoaRepository.findById(pessoaId).orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada."));
	    TarefaDTO tarefaDTO = new TarefaDTO();

	    // Verificar se a pessoa pertence ao mesmo departamento da tarefa
	    if (!pessoa.getDepartamento().equals(tarefa.getDepartamento())) {
	        tarefaDTO.setSuccess(Boolean.FALSE);
	        tarefaDTO.setMensagem("A pessoa não pertence ao mesmo departamento da tarefa.");
	        return tarefaDTO;
	    }

	    // Alocar a pessoa na tarefa
	    tarefa.setDataCriacao(LocalDateTime.now());
	    tarefa.setPessoa(pessoa);
	    tarefaRepository.save(tarefa);

	    tarefaDTO.setMensagem("A pessoa foi alocada com sucesso");
	    tarefaDTO.setSuccess(Boolean.TRUE);

	    return tarefaDTO;
	}

	public TarefaDTO finalizarTarefa(Long tarefaId) {
	    Tarefa tarefa = tarefaRepository.findById(tarefaId).orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada."));
	    TarefaDTO tarefaDTO = new TarefaDTO();

	    // Verificar se a tarefa já foi finalizada anteriormente
	    if (tarefa.isFinalizado()) {
	        tarefaDTO.setSuccess(Boolean.TRUE);
	        tarefaDTO.setMensagem("A tarefa já foi finalizada anteriormente.");
	        return tarefaDTO;
	    }

	    // Finalizar a tarefa
	    tarefa.setFinalizado(true);
	    tarefa.setDuracao(tarefa.getDuracao());
	    tarefaRepository.save(tarefa);
	    tarefaDTO.setDuracao(tarefa.getDuracao());
	    tarefaDTO.setFinalizado(true);
	    tarefaDTO.setSuccess(Boolean.TRUE);

	    return tarefaDTO;
	}

	
	public TarefaDTO removerTarefa(Long id) {
		TarefaDTO tarefaDTO = new TarefaDTO();
		Tarefa tarefa = tarefaRepository.getById(id);
		if(Objects.nonNull(tarefa)) {
			tarefaRepository.delete(tarefa);
			tarefaDTO.setMensagem("A tarefa foi removida com sucesso");
			tarefaDTO.setSuccess(Boolean.TRUE);
		} else {
			tarefaDTO.setMensagem("A tarefa não foi removida");
			tarefaDTO.setSuccess(Boolean.FALSE);
		}
		return tarefaDTO;
	}
	
	
	public List<TarefaDTO> listarTarefasPendentes() {
        List<TarefaDTO> tarefasPendentes = new ArrayList<>();
        Pageable pageable = PageRequest.of(0, 3);  // Obtém as 3 primeiras tarefas
        List<Tarefa> tarefas = tarefaRepository.findTarefasSemAlocacaoOrderByPrazo(pageable);

        for (Tarefa tarefa : tarefas) {
            TarefaDTO tarefaDTO = new TarefaDTO();
            tarefaDTO.setId(tarefa.getId());
            tarefaDTO.setDescricao(tarefa.getDescricao());
            tarefaDTO.setDepartamento(tarefa.getDepartamento().getTitulo());
            tarefaDTO.setDuracao(tarefa.getDuracao());
            tarefaDTO.setPrazo(tarefa.getPrazo());
            tarefaDTO.setTitulo(tarefa.getTitulo());
            tarefasPendentes.add(tarefaDTO);
        }

        return tarefasPendentes;
    }
	
}
