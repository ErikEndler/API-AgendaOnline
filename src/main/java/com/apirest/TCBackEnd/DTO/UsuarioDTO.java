package com.apirest.TCBackEnd.DTO;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
	
	private long id;
	@NotBlank
	private String role;
	private String nome;
	private String cpf;
	private String telefone;
	private String whatsapp;
	private String email;
	private int senha;

}
