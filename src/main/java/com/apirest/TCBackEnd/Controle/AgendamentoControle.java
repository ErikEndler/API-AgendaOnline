package com.apirest.TCBackEnd.Controle;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apirest.TCBackEnd.DTO.AgendamentoDTO;
import com.apirest.TCBackEnd.Models.Agendamento;
import com.apirest.TCBackEnd.Models.Categoria;
import com.apirest.TCBackEnd.Models.Servico;
import com.apirest.TCBackEnd.Models.Usuario;
import com.apirest.TCBackEnd.Repository.AgendamentoRepository;
import com.apirest.TCBackEnd.Repository.ServicoRepository;
import com.apirest.TCBackEnd.Repository.UsuarioRepository;
import com.apirest.TCBackEnd.Util.ResourceNotFoundException;

@Service
public class AgendamentoControle extends GenericControl<Agendamento, AgendamentoDTO, AgendamentoRepository> {

	@Autowired
	UsuarioRepository usuarioRepository;
	@Autowired
	ServicoRepository servicoRepository;

	@Override
	protected void verificaSalvar(AgendamentoDTO dto) {
		verificaCliente(dto);
		verificaServico(dto);
	}

	@Override
	protected void verificUpdate(AgendamentoDTO dto) {
		verificaExixte(dto.getId());
		verificaCliente(dto);
		verificaServico(dto);
	}

	@Override
	protected void verificaListAll() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void verificaList(long id) {
		verificaExixte(id);
	}

	@Override
	protected void verificaDeletar(long id) {
		verificaExixte(id);
	}

	@Override
	protected Agendamento transformaSalvar(AgendamentoDTO dto) {
		return new Agendamento(verificaCliente(dto), verificaServico(dto), dto.getHorario(), dto.getNotificacao(),
				dto.getObs());
	}

	@Override
	protected Agendamento transformaEditar(AgendamentoDTO dto) {
		return new Agendamento(dto.getId(), verificaCliente(dto), verificaServico(dto), dto.getHorario(),
				dto.getNotificacao(), dto.getObs());
	}

	@Override
	protected String MenssagemErro() {
		String msg = "Agendamento";
		return msg;
	}

	// --------------------- METODOS AUXILIARES------------

	private void verificaExixte(long id) {
		Optional<Agendamento> agendamento = repositorio.findById(id);
		agendamento
				.orElseThrow(() -> new ResourceNotFoundException(MenssagemErro() + " nao encontrado para o ID: " + id));
	}

	private Servico verificaServico(AgendamentoDTO dto) {
		if (dto.getServicoId() == 0) {
			new ResourceNotFoundException("Campo Serviço não informado corretamente !!");
		}
		Optional<Servico> retorno = servicoRepository.findById(dto.getServicoId());
		return retorno.orElseThrow(
				() -> new ResourceNotFoundException("Serviço nao encontrado para o ID: " + dto.getServicoId()));
	}

	private Usuario verificaCliente(AgendamentoDTO dto) {
		if (dto.getClienteId() == 0) {
			new ResourceNotFoundException("Campo Cliente não informado corretamente !!");
		}
		Optional<Usuario> retorno = usuarioRepository.findById(dto.getServicoId());
		return retorno.orElseThrow(
				() -> new ResourceNotFoundException("CLiente nao encontrado para o ID: " + dto.getServicoId()));
	}

}
