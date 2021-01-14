package com.apirest.TCBackEnd.Models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class Role implements GrantedAuthority {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String nameRole;
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "role")
	private List<Usuario> usuarios;

	@Override
	public String getAuthority() {
		return this.nameRole;
	}	
}
