package com.apirest.TCBackEnd.Models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Servico{
	
	@Id
	private long id;
	@ManyToOne
	private Categoria categoria;
	private String nome;
	private String descricao;
	@OneToMany(mappedBy = "servico")
	private List<Escala> escalas;
	@OneToMany(mappedBy = "servico")
	private List<Agendamento> servico;
	

}
