package com.apirest.TCBackEnd.Models;

import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ItemEscala {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@ManyToOne
	private Escala escala;
	private LocalTime hrInicial;
	private LocalTime hrFinal;
	private int qtd;

	public ItemEscala(long id, Escala escala, LocalTime hrInicial, LocalTime hrFinal, int qtd) {
		super();
		this.id = id;
		this.escala = escala;
		this.hrInicial = hrInicial;
		this.hrFinal = hrFinal;
		this.qtd = qtd;
	}

	public ItemEscala(Escala escala, LocalTime hrInicial, LocalTime hrFinal, int qtd) {
		super();
		this.escala = escala;
		this.hrInicial = hrInicial;
		this.hrFinal = hrFinal;
		this.qtd = qtd;
	}
	

}
