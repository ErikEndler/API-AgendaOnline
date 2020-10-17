package com.apirest.TCBackEnd.Models;

import java.time.LocalDateTime;

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
	private Servico servico;
	private LocalDateTime horario;
	private Boolean notificacao;
	private String obs;
	@OneToOne(mappedBy = "agendamento")
	private Atendimento atendimento;

	// usado para salvar
	public Agendamento(Usuario cliente, Servico servico, LocalDateTime horario, Boolean notificacao, String obs) {
		super();
		this.cliente = cliente;
		this.servico = servico;
		this.horario = horario;
		this.notificacao = notificacao;
		this.obs = obs;
	}

	// usado para o update
	public Agendamento(long id, Usuario cliente, Servico servico, LocalDateTime horario, Boolean notificacao,
			String obs) {
		super();
		this.id = id;
		this.cliente = cliente;
		this.servico = servico;
		this.horario = horario;
		this.notificacao = notificacao;
		this.obs = obs;
	}

}
