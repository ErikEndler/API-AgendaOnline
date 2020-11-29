package com.apirest.TCBackEnd.Models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Escala {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@ManyToOne
	private ServicoFuncionario servicoFuncionario;
	private String diaSemana;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "escala")
	private List<ItemEscala> itensEscala;

	public Escala(long id, ServicoFuncionario servicoFuncionario, String diaSemana) {
		super();
		this.id = id;
		this.servicoFuncionario = servicoFuncionario;
		this.diaSemana = diaSemana;
	}

	public Escala(ServicoFuncionario servicoFuncionario, String diaSemana) {
		super();
		this.servicoFuncionario = servicoFuncionario;
		this.diaSemana = diaSemana;
	}

}
