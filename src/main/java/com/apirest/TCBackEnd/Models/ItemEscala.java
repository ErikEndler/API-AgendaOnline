package com.apirest.TCBackEnd.Models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class ItemEscala {
	@Id
	private long id;
	@ManyToOne
	private Escala escala;
	private String hrInicial;
	private String hrFinal;
	private int qtd;

}
