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
	RoleControle roleControle;

	// verifica existencia de usuarios cadastrados na inicializaçao do sistema
	@EventListener(ContextRefreshedEvent.class)
	private void verificaUsers() {
		roleControle.verificaRoles();
		if (repositorio.count() == 0) {
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
		repositorio.save(usuario);
	}

	public Optional<Usuario> listarPorCpf(String cpf) {
		Optional<Usuario> retorno = repositorio.findByCpf(cpf);
		retorno.orElseThrow(
				() -> new ResourceNotFoundException(MenssagemErro() + " nao encontrado para o CPF: " + cpf));
		return retorno;
	}

//---------------------METODOS AUXILIARES-----------------------------------------
	private void verificaExiste(long id) {
		Optional<Usuario> retorno = repositorio.findById(id);
		retorno.orElseThrow(() -> new ResourceNotFoundException(MenssagemErro() + " nao encontrado para o ID: " + id));
	}

	private void verificaCpf(String cpf) {
		// Busca usuario pelo cpf
		Optional<Usuario> user = repositorio.findByCpf(cpf);
		// Verifica se variavel "user" esta vazia ou nao
		if (user.isPresent()) {
			// Se não estiver vazia retorna uma esceção conforme abaixo
			throw new ResourceNotFoundException(" Usuartio ja existenta para CPF:" + cpf);
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
	protected void verificaList(long id) {
		verificaExiste(id);
	}

	@Override
	protected void verificaDeletar(long id) {
		verificaExiste(id);
	}

	@Override
	protected void verificaSalvar(UsuarioDTO dto) {
		System.out.println("-----------ID:"+dto.getId());
		verificaCpf(dto.getCpf());
		validaRole(dto);
	}

	@Override
	protected void verificUpdate(UsuarioDTO dto) {
		verificaExiste(dto.getId());
		validaRole(dto);
	}

}
