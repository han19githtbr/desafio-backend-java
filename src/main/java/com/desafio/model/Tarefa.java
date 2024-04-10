package com.desafio.model;
  
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.desafio.view.PessoaDTO;
import com.desafio.view.TarefaDTO;

import lombok.Data;
import lombok.NoArgsConstructor;
  
@Data
@NoArgsConstructor
@Entity
@Table(name = "tarefa")
public class Tarefa {
  
  	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
  
    private String titulo;
  
    private String descricao;
  
    private LocalDate prazo;
  
    @ManyToOne
    @JoinColumn(name = "id_departamento")
    private Departamento departamento;
  
    private long duracao;
  
    private boolean finalizado;
    
    @ManyToOne
    @JoinColumn(name = "id_pessoa")
    private Pessoa pessoa;
  
    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;
  
    @Transient 
    public long getDuracao() {
        return Duration.between(dataCriacao, LocalDateTime.now()).toHours();
    }


    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ordem_apresentacao")
    private Long ordem_apresentacao;


    /*public TarefaDTO toDTO() {
    	TarefaDTO tarefaDTO = new TarefaDTO();
        tarefaDTO.setId(this.id);
        tarefaDTO.setTitulo(this.titulo);
        tarefaDTO.setDescricao(this.descricao);
        tarefaDTO.setPrazo(this.prazo);
        tarefaDTO.setDepartamento(this.departamento.getTitulo());
        tarefaDTO.setFinalizado(this.finalizado);
        tarefaDTO.setDuracao(this.getDuracao());
        return tarefaDTO;
    }*/
    
    
    public TarefaDTO toDTO() {
        TarefaDTO tarefaDTO = new TarefaDTO();
        tarefaDTO.setId(this.id);
        tarefaDTO.setTitulo(this.titulo);
        tarefaDTO.setDescricao(this.descricao);
        tarefaDTO.setPrazo(this.prazo);
        
        // Verificar se o departamento não é nulo antes de acessar seu título
        if (this.departamento != null) {
            tarefaDTO.setDepartamento(this.departamento.getTitulo());
        } else {
            tarefaDTO.setDepartamento(null); // ou outra ação adequada, dependendo do seu contexto
        }
        
        tarefaDTO.setFinalizado(this.finalizado);
        tarefaDTO.setDuracao(this.getDuracao());
        return tarefaDTO;
    }
  
}  
    