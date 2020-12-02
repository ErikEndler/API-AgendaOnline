package com.apirest.TCBackEnd.DTO;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.apirest.TCBackEnd.Models.Usuario;

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
	private String sexo;
	private String senha;
	private Boolean notificacao;
	private Boolean notificacaoEmail;
	private Boolean notificacaoWhatsapp;

	public static UsuarioDTO usuarioResposta(Usuario usuario) {
		return new UsuarioDTO(usuario.getId(), usuario.getRole().getNameRole(), usuario.getNome(), usuario.getCpf(),
				usuario.getTelefone(), usuario.getWhatsapp(), usuario.getEmail(), usuario.getSexo(), usuario.getSenha(),
				usuario.getNotificacao(), usuario.getNotificacaoEmail(), usuario.getNotificacaoWhats());
	}

	// Recebe uma lista de usuarios e transforma a lista para o formato de resposta
	public static Iterable<UsuarioDTO> listarResposta(Iterable<Usuario> listaUsuarios) {
		// Cria a lista que sera retornada
		List<UsuarioDTO> listaDTO = new ArrayList<UsuarioDTO>();
		// Faz um for na lista recebida no metodo
		for (Usuario usuario : listaUsuarios) {
			listaDTO.add(usuarioResposta(usuario));
		}
		return listaDTO;
	}

}
