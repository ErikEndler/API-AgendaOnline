package com.apirest.TCBackEnd.Models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor

public class Usuario implements UserDetails {

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
	private String sexo;
	private String senha;
	private Boolean notificacaoEmail;
	private Boolean notificacaoWhats;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente")
	private List<Agendamento> agendamentosCliente;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "funcionario")
	private List<Atendimento> atendimentosFuncionario;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "funcionario")
	private List<ServicoFuncionario> servico_funcionario;

	// @ManyToMany
	// @JoinTable(name = "usuario_servico", joinColumns = @JoinColumn(name =
	// "usuario_id", referencedColumnName = "id"), inverseJoinColumns =
	// @JoinColumn(name = "servico_id", referencedColumnName = "id"))
	// private List<Servico> servicos;

	public Usuario(long id, Role role, String nome, String cpf, String telefone, String whatsapp, String email,
			String sexo, String senha, boolean notificacaoEmail, boolean notificacaoWhats) {
		super();
		this.id = id;
		this.role = role;
		this.nome = nome;
		this.cpf = cpf;
		this.telefone = telefone;
		this.whatsapp = whatsapp;
		this.email = email;
		this.sexo = sexo;
		this.senha = senha;
		this.notificacaoEmail = notificacaoEmail;
		this.notificacaoWhats = notificacaoWhats;
	}

	public Usuario(Role role, String nome, String cpf, String string, String string2, String email, String sexo,
			String senha, boolean notificacaoEmail, boolean notificacaoWhats) {
		super();
		this.role = role;
		this.nome = nome;
		this.cpf = cpf;
		this.telefone = string;
		this.whatsapp = string2;
		this.email = email;
		this.sexo = sexo;
		this.senha = senha;
		this.notificacaoEmail = notificacaoEmail;
		this.notificacaoWhats = notificacaoWhats;
	}

	@Override
	public Collection<Role> getAuthorities() {
		Collection<Role> RRoles = new ArrayList<Role>();
		RRoles.add(role);
		return RRoles;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.senha;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.cpf;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
