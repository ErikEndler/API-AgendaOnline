package com.apirest.TCBackEnd.Controle;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apirest.TCBackEnd.DTO.AgendamentoDTO;
import com.apirest.TCBackEnd.Models.Agendamento;
import com.apirest.TCBackEnd.Models.Servico;
import com.apirest.TCBackEnd.Models.Usuario;
import com.apirest.TCBackEnd.Repository.AgendamentoRepository;
import com.apirest.TCBackEnd.Repository.ServicoRepository;
import com.apirest.TCBackEnd.Repository.UsuarioRepository;
import com.apirest.TCBackEnd.Util.DataHora;
import com.apirest.TCBackEnd.Util.ResourceNotFoundException;

@Service
public class AgendamentoControle extends GenericControl<Agendamento, AgendamentoDTO, AgendamentoRepository> {

	@Autowired
	UsuarioRepository usuarioRepository;
	@Autowired
	ServicoRepository servicoRepository;
	@Autowired
	DataHora datahora;
	
	public Iterable<Agendamento> listarPorClinete(long idCliente) {
		verificaCliente(idCliente);
		return repositorio.findAllByCliente(idCliente);
	}
	public Iterable<Agendamento> listarPorServico(long idServico) {
		verificaServico(idServico);
		return repositorio.findAllByServico(idServico);
	}

	@Override
	protected void verificaSalvar(AgendamentoDTO dto) {
		verificaCliente(dto.getClienteId());
		verificaServico(dto.getServicoId());
	}

	@Override
	protected void verificUpdate(AgendamentoDTO dto) {
		verificaExixte(dto.getId());
		verificaCliente(dto.getClienteId());
		verificaServico(dto.getServicoId());
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
		return new Agendamento(verificaCliente(dto.getClienteId()), verificaServico(dto.getServicoId()), datahora.stringemDateTime(dto.getHorario()),
				dto.getNotificacao(), dto.getObs());
	}

	@Override
	protected Agendamento transformaEditar(AgendamentoDTO dto) {
		return new Agendamento(dto.getId(), verificaCliente(dto.getClienteId()), verificaServico(dto.getServicoId()),
				datahora.stringemDateTime(dto.getHorario()), dto.getNotificacao(), dto.getObs());
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

	private Servico verificaServico(long id) {
		if (id == 0) {
			new ResourceNotFoundException("Campo Serviço não informado corretamente !!");
		}
		Optional<Servico> retorno = servicoRepository.findById(id);
		return retorno.orElseThrow(
				() -> new ResourceNotFoundException("Serviço nao encontrado para o ID: " + id));
	}

	private Usuario verificaCliente(long id) {
		if (id == 0) {
			new ResourceNotFoundException("Campo Cliente não informado corretamente !!");
		}
		Optional<Usuario> retorno = usuarioRepository.findById(id);
		return retorno.orElseThrow(
				() -> new ResourceNotFoundException("CLiente nao encontrado para o ID: " + id));
	}

	

}
