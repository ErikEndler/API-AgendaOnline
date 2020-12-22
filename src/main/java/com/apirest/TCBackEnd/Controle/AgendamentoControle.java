package com.apirest.TCBackEnd.Controle;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.apirest.TCBackEnd.DTO.AgendamentoDTO;
import com.apirest.TCBackEnd.Models.Agendamento;
import com.apirest.TCBackEnd.Models.Escala;
import com.apirest.TCBackEnd.Models.ItemEscala;
import com.apirest.TCBackEnd.Models.ServicoFuncionario;
import com.apirest.TCBackEnd.Models.Usuario;
import com.apirest.TCBackEnd.Repository.AgendamentoRepository;
import com.apirest.TCBackEnd.Repository.EscalaRepository;
import com.apirest.TCBackEnd.Repository.ItemEscalaRepository;
import com.apirest.TCBackEnd.Repository.ServicoFuncionarioRepository;
import com.apirest.TCBackEnd.Repository.UsuarioRepository;
import com.apirest.TCBackEnd.Util.DataHora;
import com.apirest.TCBackEnd.Util.Error.ResourceNotFoundException;

@Service
public class AgendamentoControle extends GenericControl<Agendamento, AgendamentoDTO, AgendamentoRepository> {

	@Autowired
	UsuarioRepository usuarioRepository;
	@Autowired
	EscalaRepository escalaRepository;
	@Autowired
	ItemEscalaRepository itemEscalaRepository;
	@Autowired
	ServicoFuncionarioRepository servicoFuncionarioRepository;
	@Autowired
	DisponibilidadeControle disponibilidadeControle;

	@Autowired
	DataHora datahora;

	// @EventListener(ContextRefreshedEvent.class)
	public List<List<Agendamento>> listaAgendamentosDiaFuncionario(List<String> listDatas, long idFuncionario) {
		List<List<Agendamento>> listaAgendamentos = new ArrayList<>();

		for (String data : listDatas) {
			List<Agendamento> agendamentos = null;
			if (data.equals("hoje")) {
				agendamentos = this.repositorio.findByHorarioAndServicoFuncionario(LocalDate.now(), idFuncionario);
			} else {
				LocalDate localdate = datahora.stringEmData(data);
				agendamentos = this.repositorio.findByHorarioAndServicoFuncionario(localdate, idFuncionario);
			}
			listaAgendamentos.add(agendamentos);
		}

		return listaAgendamentos;
	}

	public Iterable<Agendamento> listarPorCliente(long idCliente) {
		verificaCliente(idCliente);
		return repositorio.findAllByCliente(idCliente);
	}

	public Iterable<Agendamento> listarPorServico(long idServicoFuncionario) {
		verificaServicoFuncionario(idServicoFuncionario);
		return repositorio.findAllByServicoFuncionario(idServicoFuncionario);
	}

	@Override
	protected void verificaSalvar(AgendamentoDTO dto) {
		verificaCliente(dto.getClienteId());
		verificaServicoFuncionario(dto.getServicoFuncionarioId());
		verificaPreSave(dto);
	}

	@Override
	protected Agendamento verificUpdate(AgendamentoDTO dto) {
		Agendamento agendamento = verificaExixte(dto.getId()).get();
		verificaCliente(dto.getClienteId());
		verificaServicoFuncionario(dto.getServicoFuncionarioId());
		return agendamento;
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
		return new Agendamento(verificaCliente(dto.getClienteId()),
				verificaServicoFuncionario(dto.getServicoFuncionarioId()),
				datahora.stringemDateTime(dto.getHorarioInicio()), datahora.stringemDateTime(dto.getHorarioFim()),
				dto.getNotificacao(), dto.getObs());
	}

	@Override
	protected Agendamento transformaEditar(AgendamentoDTO dto) {
		return new Agendamento(dto.getId(), verificaCliente(dto.getClienteId()),
				verificaServicoFuncionario(dto.getServicoFuncionarioId()),
				datahora.stringemDateTime(dto.getHorarioInicio()), datahora.stringemDateTime(dto.getHorarioFim()),
				dto.getNotificacao(), dto.getObs());
	}

	@Override
	protected String MenssagemErro() {
		String msg = "Agendamento";
		return msg;
	}

	// --------------------- METODOS AUXILIARES------------

	private Optional<Agendamento> verificaExixte(long id) {
		Optional<Agendamento> agendamento = repositorio.findById(id);
		agendamento
				.orElseThrow(() -> new ResourceNotFoundException(MenssagemErro() + " nao encontrado para o ID: " + id));
		return agendamento;
	}

	private ServicoFuncionario verificaServicoFuncionario(long id) {
		if (id == 0) {
			throw new ResourceNotFoundException("Campo Serviço não informado corretamente !!");
		}
		Optional<ServicoFuncionario> retorno = servicoFuncionarioRepository.findById(id);
		return retorno.orElseThrow(() -> new ResourceNotFoundException("Serviço nao encontrado para o ID: " + id));
	}

	private Usuario verificaCliente(long id) {
		if (id == 0) {
			throw new ResourceNotFoundException("Campo Cliente não informado corretamente !!");
		}
		Optional<Usuario> retorno = usuarioRepository.findById(id);
		return retorno.orElseThrow(() -> new ResourceNotFoundException("Cliente nao encontrado para o ID: " + id));
	}

	@Override
	protected void posSalvar(Agendamento retorno) {
		// TODO Auto-generated method stub

	}

	// lista todos horarios do dia
	private void listar(AgendamentoDTO dto) {
		LocalDate data = datahora.stringemDateTime(dto.getHorarioInicio()).toLocalDate();

		List<Agendamento> agendamentos = repositorio.agendamentosDia(data);
	}

	private void verificaPreSave(AgendamentoDTO dto) {
		if (verificaDisponibilidadeGeral(dto) == true) {
			if (verificaEscala(dto) == true) {
				verificaHorario(dto);
			}
		}
	}

	private boolean verificaEscala(AgendamentoDTO dto) {
		DayOfWeek day = datahora.stringemDateTime(dto.getHorarioInicio()).getDayOfWeek();
		System.out.println("--day " + day);

		LocalTime hrInicial = datahora.stringemDateTime(dto.getHorarioInicio()).toLocalTime();
		LocalTime hrFinal = datahora.stringemDateTime(dto.getHorarioInicio()).toLocalTime();
		Escala escala = escalaRepository
				.findByServicoFuncionarioIdAndDiaSemana(dto.getServicoFuncionarioId(),
						day.getDisplayName(TextStyle.FULL, new Locale("pt")))
				.orElseThrow(() -> new ResourceNotFoundException("Não Há Escala"));
		ItemEscala itemEscala = itemEscalaRepository.escala(escala.getId(), hrInicial, hrFinal)
				.orElseThrow(() -> new ResourceNotFoundException("Horario fora de escala disponivel"));
		return true;
	}

	private void verificaHorario(AgendamentoDTO dto) {
		int qtd = repositorio.countChoques(datahora.stringemDateTime(dto.getHorarioInicio()),
				datahora.stringemDateTime(dto.getHorarioFim()), dto.getServicoFuncionarioId());
		if (qtd > 0) {
			throw new ResourceNotFoundException("Horario do funcionario ja ocupado");
		}
	}

	private boolean verificaDisponibilidadeGeral(AgendamentoDTO dto) {
		int qtdGeral = disponibilidadeControle.listGeral().getQtd();
		int qtd = repositorio.qtdSimultaneos(datahora.stringemDateTime(dto.getHorarioInicio()),
				datahora.stringemDateTime(dto.getHorarioFim()));
		if (qtdGeral <= qtd) {
			throw new ResourceNotFoundException("Horario esta cheio");
		}
		return true;
	}

}
