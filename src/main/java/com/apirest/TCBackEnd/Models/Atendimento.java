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
public class Atendimento {
	@Id
	private long id;
	@OneToOne
	private Agendamento agendamento;
	@ManyToOne
	private Usuario funcionario;
	private LocalDateTime inicio;
	private LocalDateTime fim;
	@OneToOne(mappedBy = "atendimento")
	private Avaliacao avaliacao;
	

}
