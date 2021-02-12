package com.apirest.TCBackEnd.Controle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apirest.TCBackEnd.DTO.AvaliacaoDTO;
import com.apirest.TCBackEnd.Models.Atendimento;
import com.apirest.TCBackEnd.Models.Avaliacao;
import com.apirest.TCBackEnd.Models.Usuario;
import com.apirest.TCBackEnd.Repository.AtendimentoRepository;
import com.apirest.TCBackEnd.Repository.AvaliacaoRepository;
import com.apirest.TCBackEnd.Util.Error.ResourceNotFoundException;

@Service
public class AvaliacaoControle extends GenericControl<Avaliacao, AvaliacaoDTO, AvaliacaoRepository> {

	@Autowired
	AtendimentoRepository atendimentoRepository;
	@Autowired
	UsuarioControle usuarioControle;

	public List<Avaliacao> minhasAvaliacoes(long idUsuario) {
		List<Avaliacao> avaliacoes = new ArrayList<>();
		Usuario usuario = usuarioControle.verificaExiste(idUsuario).get();
		if (usuario.getRole().getNameRole() == "ROLE_USER") {
			avaliacoes = repositorio.findByAtendimentoAgendamentoClienteId(idUsuario);
		} else {
			avaliacoes = repositorio.findByAtendimentoFuncionarioId(idUsuario);
		}
		return avaliacoes;
	}

	@Override
	protected void verificaSalvar(AvaliacaoDTO dto) {
		verificaAtendimento(dto.getAtendimento().getId());
	}

	@Override
	protected Avaliacao verificUpdate(AvaliacaoDTO dto) {
		Avaliacao avaliacao = verificaExiste(dto.getId()).get();
		verificaAtendimento(dto.getAtendimento().getId());
		return avaliacao;
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
	protected Avaliacao transformaSalvar(AvaliacaoDTO dto) {
		return new Avaliacao(verificaAtendimento(dto.getAtendimento().getId()), dto.getNotaCliente(),
				dto.getNotaFuncionario(), dto.getObsCliente(), dto.getObsFuncionario());
	}

	@Override
	protected Avaliacao transformaEditar(AvaliacaoDTO dto) {
		return new Avaliacao(dto.getId(), verificaAtendimento(dto.getAtendimento().getId()), dto.getNotaCliente(),
				dto.getNotaFuncionario(), dto.getObsCliente(), dto.getObsFuncionario());
	}

	@Override
	protected void posSalvar(Avaliacao avaliacao) {
		Optional<Atendimento> atendimento = atendimentoRepository.findById(avaliacao.getAtendimento().getId());
		atendimento.get().setAvaliacao(avaliacao);
		atendimentoRepository.save(atendimento.get());
	}

	public Optional<Avaliacao> findByAtendimento(long id) {
		return repositorio.findByAtendimentoId(id);
	}

	// -------------------------AUXILIARES---------------------------------

	private Atendimento verificaAtendimento(long idAtendimento) {
		if (idAtendimento == 0) {
			throw new ResourceNotFoundException("Campo Atendimento não informado corretamente !!");
		}
		Optional<Atendimento> atendimento = atendimentoRepository.findById(idAtendimento);
		return atendimento.orElseThrow(
				() -> new ResourceNotFoundException("Atendimento nao encontrado para o ID: " + idAtendimento));
	}

	private Optional<Avaliacao> verificaExiste(long id) {
		Optional<Avaliacao> avaliacao = repositorio.findById(id);
		avaliacao.orElseThrow(() -> new ResourceNotFoundException("Avaliação nao encontrado para o ID: " + id));
		return avaliacao;
	}
}
