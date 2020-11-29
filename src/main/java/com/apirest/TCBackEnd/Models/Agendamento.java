package com.apirest.TCBackEnd.Models;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Agendamento {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@ManyToOne
	private Usuario cliente;
	@ManyToOne
	private ServicoFuncionario servicoFuncionario;
	private LocalDateTime horario;
	private LocalDateTime horarioFim;

	private Boolean notificacao;
	private String obs;
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "agendamento")
	private Atendimento atendimento;

	// usado para salvar
	public Agendamento(Usuario cliente, ServicoFuncionario servicoFuncionario, LocalDateTime horario, Boolean notificacao, String obs) {
		super();
		this.cliente = cliente;
		this.servicoFuncionario = servicoFuncionario;
		this.horario = horario;
		this.notificacao = notificacao;
		this.obs = obs;
	}

	// usado para o update
	public Agendamento(long id, Usuario cliente, ServicoFuncionario servicoFuncionario, LocalDateTime horario, Boolean notificacao,
			String obs) {
		super();
		this.id = id;
		this.cliente = cliente;
		this.servicoFuncionario = servicoFuncionario;
		this.horario = horario;
		this.notificacao = notificacao;
		this.obs = obs;
	}

}
