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
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@ManyToOne
	private Role role;
	
	private String nome;
	private String cpf;
	private String telefone;
	private String whatsapp;
	private String email;
	private int senha;
	@OneToMany(mappedBy = "cliente")
	private List<Agendamento> agendamentosCliente;
	@OneToMany(mappedBy = "funcionario")
	private List<Atendimento> atendimentosFuncionario;

	public Usuario(long id, Role role, String nome, String cpf, String telefone, String whatsapp, String email,
			int senha) {
		super();
		this.id = id;
		this.role = role;
		this.nome = nome;
		this.cpf = cpf;
		this.telefone = telefone;
		this.whatsapp = whatsapp;
		this.email = email;
		this.senha = senha;
	}

	public Usuario(Role role, String nome, String cpf, String string, String string2, String email, int senha) {
		super();
		this.role = role;
		this.nome = nome;
		this.cpf = cpf;
		this.telefone = string;
		this.whatsapp = string2;
		this.email = email;
		this.senha = senha;
	}

}
