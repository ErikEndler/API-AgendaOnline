package com.apirest.TCBackEnd.Controle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.apirest.TCBackEnd.DTO.UsuarioDTO;
import com.apirest.TCBackEnd.Email.ServiceEmail;
import com.apirest.TCBackEnd.Models.Agendamento;
import com.apirest.TCBackEnd.Models.Role;
import com.apirest.TCBackEnd.Models.Usuario;
import com.apirest.TCBackEnd.Repository.AgendamentoRepository;
import com.apirest.TCBackEnd.Repository.RoleRespository;
import com.apirest.TCBackEnd.Repository.UsuarioRepository;
import com.apirest.TCBackEnd.Util.StatusAgendamento;
import com.apirest.TCBackEnd.Util.Error.ResourceNotFoundException;

@Service
public class UsuarioControle extends GenericControl<Usuario, UsuarioDTO, UsuarioRepository> {

	@Autowired
	RoleRespository rolereposiRespository;

	@Autowired
	RoleControle roleControle;

	@Autowired
	ServiceEmail serviceEmail;

	@Autowired
	NotificacaoControle notificacaoControle;
	@Autowired
	AgendamentoControle agendamentoControle;
	@Autowired
	AgendamentoRepository agendamentoRepository;

	// Notificaçoes Funcionario ao logar
	public List<Integer> buscaNotificacoesUsuario(long idFunc) {
		List<Integer> lista = new ArrayList<>();
		int qtdAgendado = agendamentoControle.listarAgendamentosPorStatus(idFunc,
				StatusAgendamento.AGENDADO).size();
		lista.add(qtdAgendado);
		int qtdPendente = agendamentoControle.listarAgendamentosPorStatus(idFunc,
				StatusAgendamento.PENDENTE).size();
		lista.add(qtdPendente);		
		int qtdNaoAtendido =agendamentoControle.listarAgendamentosPorStatus(idFunc,
				StatusAgendamento.NAOATENDIDO).size() ;
		lista.add(qtdNaoAtendido);
		System.out.println("----LISTA  = " + lista);
		return lista;
	}

	// Score cliente
	public int scoreCliente(long id) {
		// busca os agendamentos do cliente
		List<Agendamento> agendamentos = (List<Agendamento>) agendamentoControle.listarPorCliente(id);
		// Filtra os agendamentos que cliente faltou
		List<Agendamento> agendamentoFaltou = agendamentos.stream()
				.filter(a -> a.getStatus() == StatusAgendamento.FALTOU).collect(Collectors.toList());
		int qtd = agendamentoFaltou.size();
		return qtd;
	}

	public List<Integer> buscaNotificacoesCliente(long idCliente) {
		List<Integer> lista = new ArrayList<>();
		List<Agendamento> agendados = agendamentoRepository.findByClienteIdAndStatusOrderByHorarioDesc(idCliente,
				StatusAgendamento.AGENDADO);
		int qtdAgendado = agendados.size();
		lista.add(qtdAgendado);
		List<Agendamento> pendentes = agendamentoRepository.findByClienteIdAndStatusOrderByHorarioDesc(idCliente,
				StatusAgendamento.PENDENTE);
		int qtdPendente = pendentes.size();
		lista.add(qtdPendente);
		System.out.println("----LISTA  = " + lista);
		return lista;
	}

	// metodo para criar nova senha
	public void trocarSenha(String cpf, String email) {
		Usuario usuario = listarPorCpf(cpf).get();
		if (usuario.getEmail() != email) {
			throw new ResourceNotFoundException("Email não coencide");
		}
		String novaSenha = novaSenha();
		salvaNovaSenha(novaSenha, usuario);
	}

	// metodo q gera uma numeraçao para nova senha
	private String novaSenha() {
		Random random = new Random();
		int numeroInteiroAleatorio = random.nextInt((9000000 - 100000) + 1) + 100000;
		String novaSenha = String.valueOf(numeroInteiroAleatorio);
		return novaSenha;
	}

	// salva nova senha criptografada
	private void salvaNovaSenha(String novaSenha, Usuario user) {
		String senhaCriptografada = new BCryptPasswordEncoder().encode(novaSenha);
		user.setSenha(senhaCriptografada);
		repositorio.save(user);
	}

	// lista usuarios que nao sao da role 'ROLE_USER'
	public List<Usuario> listarNaoClientes() {
		List<Usuario> funcionarios = repositorio.listarFuncionarios();
		return funcionarios;
	}

	// verifica existencia de usuarios cadastrados na inicializaçao do sistema
	@EventListener(ContextRefreshedEvent.class)
	private void verificaUsers() {
		roleControle.verificaRoles();
		if (repositorio.count() == 0) {
			System.out.println("Sistema não possui Usuarios cadastrados !!!");
			System.out.println("Iniciando Inserção de Usuario default....");
			cadastrarUsuarios();
		}
	}

	// cadastra um usuario padrao admin no banco
	private void cadastrarUsuarios() {
		Usuario usuario = new Usuario();
		usuario.setCpf("99999999999");
		usuario.setNome("admin");
		usuario.setSenha(new BCryptPasswordEncoder().encode("123"));
		usuario.setRole(rolereposiRespository.findByNameRole("ROLE_ADMIN").get());
		repositorio.save(usuario);
		notificacaoControle.notificacaoCriacao(usuario);
	}

	// Busca um cliente pelo cpf
	public Optional<Usuario> listarPorCpf(String cpf) {
		Optional<Usuario> retorno = repositorio.findByCpf(cpf);
		retorno.orElseThrow(() -> new ResourceNotFoundException("Usuario nao encontrado para o CPF: " + cpf));
		return retorno;
	}

//---------------------METODOS AUXILIARES-----------------------------------------
	public Optional<Usuario> verificaExiste(long id) {
		Optional<Usuario> retorno = repositorio.findById(id);
		retorno.orElseThrow(() -> new ResourceNotFoundException("Usuario nao encontrado para o ID: " + id));
		return retorno;
	}

	private void verificaCpf(String cpf) {
		// Busca usuario pelo cpf
		Optional<Usuario> user = repositorio.findByCpf(cpf);
		// Verifica se variavel "user" esta vazia ou nao
		if (user.isPresent()) {
			// Se não estiver vazia retorna uma esceção conforme abaixo
			throw new ResourceNotFoundException(" Usuario ja existenta para CPF:" + cpf);
		}
	}

	protected Usuario transformaSalvar(UsuarioDTO usuarioDTO) {
		return new Usuario(buscaRole(usuarioDTO.getRole()), usuarioDTO.getNome(), usuarioDTO.getCpf(),
				usuarioDTO.getTelefone(), usuarioDTO.getWhatsapp(), usuarioDTO.getEmail(), usuarioDTO.getSexo(),
				senhaCripto(usuarioDTO, "save"), usuarioDTO.getNotificacaoEmail(), usuarioDTO.getNotificacaoWhatsapp());
	}

	protected Usuario transformaEditar(UsuarioDTO usuarioDTO) {
		return new Usuario(usuarioDTO.getId(), buscaRole(usuarioDTO.getRole()), usuarioDTO.getNome(),
				usuarioDTO.getCpf(), usuarioDTO.getTelefone(), usuarioDTO.getWhatsapp(), usuarioDTO.getEmail(),
				usuarioDTO.getSexo(), senhaCripto(usuarioDTO, "edite"), usuarioDTO.getNotificacaoEmail(),
				usuarioDTO.getNotificacaoWhatsapp());
	}

	// auxilia na ediçao de um usuario para n perder a senha ou salvar errado
	private String senhaCripto(UsuarioDTO usuarioDTO, String strings) {
		if (strings.equals("edite")) {
			Usuario oldUser = verificaExiste(usuarioDTO.getId()).get();
			if (oldUser.getSenha().equals(usuarioDTO.getSenha()) || usuarioDTO.getSenha()==null) {
				System.out.println("entrou no sdegundo if");
				return oldUser.getSenha();
			}
		}
		String senha = new BCryptPasswordEncoder().encode(usuarioDTO.getSenha());
		return senha;
	}

	private Role buscaRole(String roleString) {
		Optional<Role> role = rolereposiRespository.findByNameRole(roleString);
		role.orElseThrow(() -> new ResourceNotFoundException(" ROLE informada esta com fotmato invalido !!"));
		return role.get();
	}

	private void validaRole(UsuarioDTO dto) {
		if (dto.getRole().isEmpty()) {
			throw new ResourceNotFoundException(" Campo role esta vazio! ");
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
		dto.setNotificacaoEmail(Optional.ofNullable(dto.getNotificacaoEmail()).orElse(false));
		dto.setNotificacaoWhatsapp(Optional.ofNullable(dto.getNotificacaoWhatsapp()).orElse(false));
	}

	@Override
	protected Usuario verificUpdate(UsuarioDTO dto) {
		Usuario retorno = verificaExiste(dto.getId()).get();
		verificaNotificacoes(dto);
		validaRole(dto);
		return retorno;
	}

	@Override
	protected void posSalvar(Usuario usuario) {
		// TODO Auto-generated method stub

	}

}
