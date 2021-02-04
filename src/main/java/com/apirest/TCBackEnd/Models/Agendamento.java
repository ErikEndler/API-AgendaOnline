package com.apirest.TCBackEnd.Models;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.apirest.TCBackEnd.Util.StatusAgendamento;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor


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
	private StatusAgendamento status;

	// usado para salvar
	public Agendamento(Usuario cliente, ServicoFuncionario servicoFuncionario, LocalDateTime horario,
			LocalDateTime horarioFim, Boolean notificacao, String obs, StatusAgendamento status) {
		super();
		this.cliente = cliente;
		this.servicoFuncionario = servicoFuncionario;
		this.horario = horario;
		this.horarioFim = horarioFim;
		this.notificacao = notificacao;
		this.obs = obs;
		this.status = status;

	}

	// usado para o update
	public Agendamento(long id, Usuario cliente, ServicoFuncionario servicoFuncionario, LocalDateTime horario,
			LocalDateTime horarioFim, Boolean notificacao, String obs, StatusAgendamento status) {
		super();
		this.id = id;
		this.cliente = cliente;
		this.servicoFuncionario = servicoFuncionario;
		this.horario = horario;
		this.horarioFim = horarioFim;
		this.notificacao = notificacao;
		this.obs = obs;
		this.status = status;
	}

}
