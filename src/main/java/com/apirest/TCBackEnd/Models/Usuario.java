package com.apirest.TCBackEnd.Models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.PrimaryKeyJoinColumn;

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

	// @ManyToMany
	// @JoinTable(name = "usuario_role",
	// joinColumns = @JoinColumn(name="usuario_id", referencedColumnName = "id"),
	// inverseJoinColumns = @JoinColumn(name="role_id",referencedColumnName = "id")
	// )
	@ManyToMany
	
	@JoinTable(name = "usuario_servico", joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "servico_id", referencedColumnName = "id"))
	
	private List<Servico> servicos;

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
