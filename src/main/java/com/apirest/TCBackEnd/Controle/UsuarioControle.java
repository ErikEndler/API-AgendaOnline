package com.apirest.TCBackEnd.Controle;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.apirest.TCBackEnd.DTO.UsuarioDTO;
import com.apirest.TCBackEnd.Models.Role;
import com.apirest.TCBackEnd.Models.Usuario;
import com.apirest.TCBackEnd.Repository.RoleRespository;
import com.apirest.TCBackEnd.Repository.UsuarioRespository;
import com.apirest.TCBackEnd.Util.ResourceNotFoundException;

@Service
public class UsuarioControle extends GenericControl<Usuario, UsuarioDTO, UsuarioRespository> {

	@Autowired
	RoleRespository rolereposiRespository;
	@Autowired
	UsuarioRespository usuarioRespository;
	@Autowired
	RoleControle roleControle;

	// verifica existencia de usuarios cadastrados na inicializaçao do sistema
	@EventListener(ContextRefreshedEvent.class)
	private void verificaUsers() {
		roleControle.verificaRoles();
		if (usuarioRespository.count() == 0) {
			System.out.println("Sistema não possui Usuarios cadastrados !!!");
			System.out.println("Iniciando Incersao de Usuario default....");
			cadastrarUsuarios();
		}
	}

	private void cadastrarUsuarios() {
		Usuario usuario = new Usuario();
		usuario.setCpf("00000000000");
		usuario.setNome("admin");
		usuario.setSenha(123);
		usuario.setRole(rolereposiRespository.findByNameRole("ROLE_ADMIN").get());
		usuarioRespository.save(usuario);
	}

//---------------------METODOS AUXILIARES-----------------------------------------
	private void verificaExiste(long id) {
		Optional<Usuario> retorno = usuarioRespository.findById(id);
		retorno.orElseThrow(() -> new ResourceNotFoundException(MenssagemErro() + " nao encontrado para o ID: " + id));
	}

	private void verificaCpf(String cpf) {
		Usuario user = usuarioRespository.findByCpf(cpf);
		if (user != null) {
			throw new ResourceNotFoundException(MenssagemErro() + " ja existente para o  CPF: " + user.getCpf());
		}
	}

	protected Usuario transformaSalvar(UsuarioDTO usuarioDTO) {
		return new Usuario(buscaRole(usuarioDTO.getRole()), usuarioDTO.getNome(), usuarioDTO.getCpf(),
				usuarioDTO.getTelefone(), usuarioDTO.getWhatsapp(), usuarioDTO.getEmail(), usuarioDTO.getSenha());
	}

	protected Usuario transformaEditar(UsuarioDTO usuarioDTO) {
		return new Usuario(usuarioDTO.getId(), buscaRole(usuarioDTO.getRole()), usuarioDTO.getNome(),
				usuarioDTO.getCpf(), usuarioDTO.getTelefone(), usuarioDTO.getWhatsapp(), usuarioDTO.getEmail(),
				usuarioDTO.getSenha());
	}

	private Role buscaRole(String roleString) {
		Optional<Role> role = rolereposiRespository.findByNameRole(roleString);
		role.orElseThrow(() -> new ResourceNotFoundException(" ROLE informada esta com fotmato invalido !!"));
		return role.get();
	}

	protected String MenssagemErro() {
		String msg = "Usuario";
		return msg;
	}

	private void validaRole(UsuarioDTO dto) {
		if (dto.getRole().isEmpty()) {
			throw new ResourceNotFoundException(MenssagemErro() + " Campo role esta vazio! ");
		}
	}

	@Override
	protected void verificaListAll() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void verificaList(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void verificaDeletar(long id) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void verificaSalvar(UsuarioDTO dto) {
		verificaCpf(dto.getCpf());
		validaRole(dto);
	}

	@Override
	protected void verificUpdate(UsuarioDTO dto) {
		validaRole(dto);
	}

}
