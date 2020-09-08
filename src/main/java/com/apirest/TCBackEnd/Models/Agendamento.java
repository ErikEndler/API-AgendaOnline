package com.apirest.TCBackEnd.Models;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Agendamento {
	@Id
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

}
