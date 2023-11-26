package com.desafio.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desafio.model.Departamento;
import com.desafio.model.Pessoa;
import com.desafio.model.Tarefa;
import com.desafio.repository.DepartamentoRepository;
import com.desafio.repository.PessoaRepository;
import com.desafio.view.DepartamentoDTO;
import com.desafio.view.PessoaDTO;

@Service
public class PessoaService {

	@Autowired
    private PessoaRepository pessoaRepository;
	
	@Autowired
    private DepartamentoRepository departamentoRepository;
	
	
	public PessoaDTO salvarPessoa(Pessoa pessoa) {
	    PessoaDTO pessoaDTO = new PessoaDTO();
	    
	    if (pessoa.getNome() == null || pessoa.getDepartamento() == null || pessoa.getDepartamento().getId() == null) {
	        pessoaDTO.setSuccess(Boolean.FALSE);
	        pessoaDTO.setMensagem("O nome e o departamento não podem ser nulos");
	        return pessoaDTO;
	    }
	    
	    Departamento departamento = departamentoRepository.findById(pessoa.getDepartamento().getId()).orElse(null);
	    if (departamento == null) {
	        pessoaDTO.setSuccess(Boolean.FALSE);
	        pessoaDTO.setMensagem("Esse departamento não existe");
	        return pessoaDTO;
	    }
	    
	    pessoaRepository.save(pessoa);
	    
	    pessoaDTO.setMensagem(pessoa.getNome() + " foi salvo(a) com sucesso");
	    
	    pessoaDTO.setSuccess(Boolean.TRUE);
	    return pessoaDTO;
	 	    
	}	

	public PessoaDTO removerPessoa(Long id) {
		PessoaDTO pessoaDTO = new PessoaDTO();
		Pessoa pessoa = pessoaRepository.getById(id);
		if(Objects.nonNull(pessoa)) {
			pessoaRepository.delete(pessoa);
			pessoaDTO.setMensagem("A pessoa foi removida com sucesso");
			pessoaDTO.setSuccess(Boolean.TRUE);
		} else {
			pessoaDTO.setMensagem("A pessoa não foi removida");
			pessoaDTO.setSuccess(Boolean.FALSE);
		}
		return pessoaDTO;
	}

	
	public PessoaDTO alterarPessoa(Long id, Pessoa pessoa) {
		PessoaDTO pessoaDTO = new PessoaDTO();
		Pessoa pessoaModel = pessoaRepository.checkId(id);
		if(Objects.nonNull(pessoaModel)) {
			pessoaModel.setNome(pessoa.getNome());
			pessoaRepository.save(pessoaModel);
			pessoaDTO.setMensagem("A pessoa foi alterada com sucesso");
			pessoaDTO.setSuccess(Boolean.TRUE);
		} else {
			pessoaDTO.setMensagem("A pessoa não foi alterada");
			pessoaDTO.setSuccess(Boolean.FALSE);
		}
		return pessoaDTO;
	}
	

	public List<PessoaDTO> getAllPessoa() {
		List<Pessoa> pessoas = pessoaRepository.getAllPessoa();
	    List<PessoaDTO> pessoasDTO = new ArrayList<>();

	    for (Pessoa pessoa : pessoas) {
	        pessoasDTO.add(pessoa.toDTO());
	    }

	    return pessoasDTO;
	}
	
	public PessoaDTO buscarPorNome(String nome, LocalDateTime dataCriacao, long duracao) {
	    PessoaDTO pessoaDTO = new PessoaDTO();
	    List<Pessoa> pessoas = pessoaRepository.findByName(nome);

	    if (pessoas.isEmpty()) {
	        pessoaDTO.setMensagem("A pessoa não existe");
	        pessoaDTO.setSuccess(Boolean.FALSE);
	        return pessoaDTO;
	    }

	    List<Tarefa> tarefas = pessoaRepository.findByNameAndPeriod(nome, dataCriacao, duracao);
	    
	    if (tarefas.isEmpty()) {
	        pessoaDTO.setMensagem("A tarefa não existe");
	        pessoaDTO.setSuccess(Boolean.FALSE);
	        return pessoaDTO;
	    }

	    double mediaHorasPorTarefa = tarefas.stream().mapToLong(Tarefa::getDuracao).average().orElse(0.0);
	    pessoaDTO.setMensagem("A média de horas gastas por tarefa é: " + mediaHorasPorTarefa);
	    pessoaDTO.setSuccess(Boolean.TRUE);
	    return pessoaDTO;
	}
}
