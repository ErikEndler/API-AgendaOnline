package com.apirest.TCBackEnd.Models;

import java.util.List;

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
public class Servico {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@ManyToOne
	private Categoria categoria;
	private String nome;
	private String descricao;

	@OneToMany(mappedBy = "servico")
	private List<Escala> escalas;

	@OneToMany(mappedBy = "servico")
	private List<Agendamento> servico;

	@OneToMany(mappedBy = "servico")
	private List<ServicoFuncionario> servico_funcionario;

}
