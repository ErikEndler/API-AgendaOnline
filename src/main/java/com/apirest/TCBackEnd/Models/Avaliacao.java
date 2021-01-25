package com.apirest.TCBackEnd.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Avaliacao {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@OneToOne
	private Atendimento atendimento;
	private int notaCliente;
	private int notaFuncionario;
	private String obsCliente;
	private String obsuncionario;
	
	public Avaliacao(Atendimento atendimento, int notaCliente, int notaFuncionario, String obsCliente,
			String obsuncionario) {
		super();
		this.atendimento = atendimento;
		this.notaCliente = notaCliente;
		this.notaFuncionario = notaFuncionario;
		this.obsCliente = obsCliente;
		this.obsuncionario = obsuncionario;
	}
	
	

}
