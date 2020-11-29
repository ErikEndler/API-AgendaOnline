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
public class Atendimento {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@OneToOne
	private Agendamento agendamento;
	@ManyToOne
	private Usuario funcionario;
	private LocalDateTime inicio;
	private LocalDateTime fim;
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "atendimento")
	private Avaliacao avaliacao;

	public Atendimento(long id, Agendamento agendamento, Usuario funcionario, LocalDateTime inicio, LocalDateTime fim) {
		super();
		this.id = id;
		this.agendamento = agendamento;
		this.funcionario = funcionario;
		this.inicio = inicio;
		this.fim = fim;
	}

	public Atendimento(Agendamento agendamento, Usuario funcionario, LocalDateTime inicio, LocalDateTime fim) {
		super();
		this.agendamento = agendamento;
		this.funcionario = funcionario;
		this.inicio = inicio;
		this.fim = fim;
	}

}
