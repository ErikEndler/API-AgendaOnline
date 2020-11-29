package com.apirest.TCBackEnd.Controle;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.apirest.TCBackEnd.DTO.AtendimentoDTO;
import com.apirest.TCBackEnd.Models.Agendamento;
import com.apirest.TCBackEnd.Models.Atendimento;
import com.apirest.TCBackEnd.Models.Usuario;
import com.apirest.TCBackEnd.Repository.AgendamentoRepository;
import com.apirest.TCBackEnd.Repository.AtendimentoRepository;
import com.apirest.TCBackEnd.Repository.UsuarioRepository;
import com.apirest.TCBackEnd.Util.DataHora;
import com.apirest.TCBackEnd.Util.ResourceNotFoundException;

public class AtendimentoControle extends GenericControl<Atendimento, AtendimentoDTO, AtendimentoRepository> {

	@Autowired
	AgendamentoRepository agendamentoRepository;
	@Autowired
	UsuarioRepository usuarioRepository;
	@Autowired
	DataHora datahora;

	@Override
	protected void verificaSalvar(AtendimentoDTO dto) {
		verificaAgendamento(dto.getAgendamento());
		verificaFuncionario(dto.getFuncionario());
	}

	@Override
	protected void verificUpdate(AtendimentoDTO dto) {
		verificaExixte(dto.getId());
		verificaAgendamento(dto.getAgendamento());
		verificaFuncionario(dto.getFuncionario());
	}

	@Override
	protected void verificaListAll() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void verificaList(long id) {
		verificaExixte(id);
		verificaAgendamento(id);
		verificaFuncionario(id);
	}

	@Override
	protected void verificaDeletar(long id) {
		verificaExixte(id);
	}

	@Override
	protected Atendimento transformaSalvar(AtendimentoDTO dto) {
		return new Atendimento(verificaAgendamento(dto.getAgendamento()), verificaFuncionario(dto.getFuncionario()),
				datahora.stringemDateTime(dto.getInicio()), datahora.stringemDateTime(dto.getFim()));
	}

	@Override
	protected Atendimento transformaEditar(AtendimentoDTO dto) {
		return new Atendimento(dto.getId(), verificaAgendamento(dto.getAgendamento()),
				verificaFuncionario(dto.getFuncionario()), datahora.stringemDateTime(dto.getInicio()),
				datahora.stringemDateTime(dto.getFim()));
	}

	@Override
	protected String MenssagemErro() {
		String msg = "Atendimento";
		return msg;
	}

	// --------------------------------
	private void verificaExixte(long id) {
		Optional<Atendimento> atendimento = repositorio.findById(id);
		atendimento
				.orElseThrow(() -> new ResourceNotFoundException(MenssagemErro() + " nao encontrado para o ID: " + id));
	}

	private Agendamento verificaAgendamento(long id) {
		if (id == 0)
			throw new ResourceNotFoundException("Campo Agendamento não informado corretamente !!");
		Optional<Agendamento> agendamento = agendamentoRepository.findById(id);
		return agendamento
				.orElseThrow(() -> new ResourceNotFoundException("Agendamento nao encontrado para o ID: " + id));
	}

	private Usuario verificaFuncionario(long id) {
		if (id == 0)
			throw new ResourceNotFoundException("Campo Funcionário não informado corretamente !!");
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		return usuario.orElseThrow(() -> new ResourceNotFoundException("Funcionário nao encontrado para o ID: " + id));
	}

	@Override
	protected void posSalvar(Atendimento modelo) {
		// TODO Auto-generated method stub

	}

}
