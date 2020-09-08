package com.apirest.TCBackEnd.DTO;

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
	private String role;
	private String nome;
	private String cpf;
	private int telefone;
	private int whatsapp;
	private String email;
	private int senha;

}
