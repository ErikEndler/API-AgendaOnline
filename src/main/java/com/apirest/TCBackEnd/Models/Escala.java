package com.apirest.TCBackEnd.Models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Escala {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@ManyToOne
	private Servico servico;
	private String diaSemana;
	@OneToMany(mappedBy = "escala")
	private List<ItemEscala> itensEscala;

	public Escala(long id, Servico servico, String diaSemana) {
		super();
		this.id = id;
		this.servico = servico;
		this.diaSemana = diaSemana;
	}

	public Escala(Servico servico, String diaSemana) {
		super();
		this.servico = servico;
		this.diaSemana = diaSemana;
	}

}
