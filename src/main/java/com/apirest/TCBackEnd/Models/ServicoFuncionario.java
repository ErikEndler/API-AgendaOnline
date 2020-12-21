package com.apirest.TCBackEnd.Models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name = "servico_funcionario")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ServicoFuncionario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@ManyToOne
	private Usuario funcionario;
	@ManyToOne
	private Servico servico;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "servicoFuncionario")
	private List<Agendamento> agendamento;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "servicoFuncionario")
	private List<Escala> escalas;

	public ServicoFuncionario(Usuario funcionario, Servico servico) {
		super();
		this.funcionario = funcionario;
		this.servico = servico;
	}

	public ServicoFuncionario(long id, Usuario funcionario, Servico servico) {
		super();
		this.id = id;
		this.funcionario = funcionario;
		this.servico = servico;
	}

}
