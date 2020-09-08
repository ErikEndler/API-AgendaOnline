package com.apirest.TCBackEnd.Models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Categoria {
	@Id
	private long id;
	private String nome;
	private String descricao;
	@OneToMany(mappedBy = "categoria")
	private List<Servico> servicos;
	

}
