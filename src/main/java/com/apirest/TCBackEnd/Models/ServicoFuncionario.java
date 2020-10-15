package com.apirest.TCBackEnd.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Table(name = "servico_funcionario")
@Entity
@Getter
@Setter
@AllArgsConstructor
public class ServicoFuncionario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	private Usuario funcionario;
	@ManyToOne
	private Servico servico;

	public ServicoFuncionario(Usuario funcionario, Servico servico) {
		super();
		this.funcionario = funcionario;
		this.servico = servico;
	}

}
