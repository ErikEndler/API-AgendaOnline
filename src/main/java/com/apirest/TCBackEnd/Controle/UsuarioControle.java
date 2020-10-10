package com.apirest.TCBackEnd.Controle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.apirest.TCBackEnd.DTO.UsuarioDTO;
import com.apirest.TCBackEnd.Models.Role;
import com.apirest.TCBackEnd.Models.Usuario;
import com.apirest.TCBackEnd.Repository.RoleRespository;
import com.apirest.TCBackEnd.Repository.UsuarioRepository;
import com.apirest.TCBackEnd.Util.ResourceNotFoundException;

@Service
public class UsuarioControle extends GenericControl<Usuario, UsuarioDTO, UsuarioRepository> {

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
		usuario.setCpf("99999999999");
		usuario.setNome("admin");
		usuario.setSenha(new BCryptPasswordEncoder().encode("123"));
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
				usuarioDTO.getTelefone(), usuarioDTO.getWhatsapp(), usuarioDTO.getEmail(), usuarioDTO.getSexo(),
				senhaCripto(usuarioDTO.getSenha()), usuarioDTO.getNotificacao(), usuarioDTO.getNotificacaoEmail(),
				usuarioDTO.getNotificacaoSms(), usuarioDTO.getNotificacaoWhats());
	}

	protected Usuario transformaEditar(UsuarioDTO usuarioDTO) {
		return new Usuario(usuarioDTO.getId(), buscaRole(usuarioDTO.getRole()), usuarioDTO.getNome(),
				usuarioDTO.getCpf(), usuarioDTO.getTelefone(), usuarioDTO.getWhatsapp(), usuarioDTO.getSexo(),
				usuarioDTO.getEmail(), senhaCripto(usuarioDTO.getSenha()), usuarioDTO.getNotificacao(),
				usuarioDTO.getNotificacaoEmail(), usuarioDTO.getNotificacaoSms(), usuarioDTO.getNotificacaoWhats());
	}

	private String senhaCripto(String senha) {
		senha = new BCryptPasswordEncoder().encode(senha);
		return senha;
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
		verificaNotificacoes(dto);
		verificaCpf(dto.getCpf());
		validaRole(dto);
	}

	private void verificaNotificacoes(UsuarioDTO dto) {
		dto.setNotificacao(Optional.ofNullable(dto.getNotificacao()).orElse(false));
		dto.setNotificacaoEmail(Optional.ofNullable(dto.getNotificacaoEmail()).orElse(false));
		dto.setNotificacaoSms(Optional.ofNullable(dto.getNotificacaoSms()).orElse(false));
		dto.setNotificacaoWhats(Optional.ofNullable(dto.getNotificacaoWhats()).orElse(false));
	}

	@Override
	protected void verificUpdate(UsuarioDTO dto) {
		verificaExiste(dto.getId());
		verificaNotificacoes(dto);
		validaRole(dto);
	}

}
