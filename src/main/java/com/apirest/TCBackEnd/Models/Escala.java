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
public class Escala {
	@Id
	private long id;
	@ManyToOne
	private Servico servico;
	private String diaSemana;
	@OneToMany(mappedBy = "escala")
	private List<ItemEscala> itensEscala;
	 

}
