package com.apirest.TCBackEnd.Controle;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apirest.TCBackEnd.DTO.AgendamentoDTO;
import com.apirest.TCBackEnd.Models.Agendamento;
import com.apirest.TCBackEnd.Models.Escala;
import com.apirest.TCBackEnd.Models.ItemEscala;
import com.apirest.TCBackEnd.Models.ServicoFuncionario;
import com.apirest.TCBackEnd.Models.TimeLine;
import com.apirest.TCBackEnd.Models.Usuario;
import com.apirest.TCBackEnd.Repository.AgendamentoRepository;
import com.apirest.TCBackEnd.Repository.EscalaRepository;
import com.apirest.TCBackEnd.Repository.ItemEscalaRepository;
import com.apirest.TCBackEnd.Repository.ServicoFuncionarioRepository;
import com.apirest.TCBackEnd.Repository.UsuarioRepository;
import com.apirest.TCBackEnd.Util.DataHora;
import com.apirest.TCBackEnd.Util.StatusAgendamento;
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

	// Lista os agendamentos por status de um funcioanrio
	public List<Agendamento> listarAgendamentosPorStatus(long id, StatusAgendamento status) {
		List<Agendamento> lista = repositorio.findByServicoFuncionarioFuncionarioIdAndStatusOrderByHorario(id, status);
		return lista;
	}

	// Lista os atendimentos atendiveis do dia de um funcionario
	public List<Agendamento> listarAgendamentosAtendiveisDia(long id) {
		List<Agendamento> lista = repositorio.listarAgendamentosAtendiveis(LocalDate.now(), id);
		return lista;
	}

	public List<String> listaStatus() {
		StatusAgendamento[] statusS = StatusAgendamento.values();
		List<String> listaStatus = new ArrayList<>();
		for (StatusAgendamento status : statusS) {
			listaStatus.add(status.name());
		}
		return listaStatus;
	}

	// metodo auxiliar timeLineFuncionario
	private TimeLine adicionaLista(boolean situacao, String horaI, String horaF) {
		TimeLine timeLine = new TimeLine();
		timeLine.setHoraF(horaF);
		timeLine.setHoraI(horaI);
		timeLine.setSituacao(situacao);
		return timeLine;
	}

	// mosta lista de horario livre/ocupado do funcionario
	public List<?> timeLineFuncionario(String data, long IdServicoiFuncionario) {
		String diaSemana = datahora.pegaDiaSemana(datahora.stringEmData(data));
		Escala escala = escalaRepository.findByServicoFuncionarioIdAndDiaSemana(IdServicoiFuncionario, diaSemana)
				.orElseThrow(() -> new ResourceNotFoundException(
						MenssagemErro() + " nao encontrado para o ID: " + IdServicoiFuncionario));
		;

		List<ItemEscala> listItemEscala = (List<ItemEscala>) itemEscalaRepository.findAllByEscalaId(escala.getId());
		ServicoFuncionario servicoFuncionario = servicoFuncionarioRepository.findById(IdServicoiFuncionario).get();
		List<Agendamento> listAgendamentos = repositorio.findByHorarioAndServicoFuncionario(datahora.stringEmData(data),
				servicoFuncionario.getFuncionario().getId());
		System.out.println("-------- size " + listAgendamentos.size());

		List<TimeLine> listaFinal = new ArrayList<>();
		System.out.println("-------- size " + listAgendamentos.size());
		if (listItemEscala.size() > 0) {
			ItemEscala itemEscala = listItemEscala.get(0);
			if (listAgendamentos.size() > 0) {
				IntStream.range(0, listAgendamentos.size()).forEach(idx -> {
					System.out.println("-------- index " + idx);
					// para o primeiro indice
					if (idx == 0) {
						// if T1>T2
						if (listAgendamentos.get(idx).getHorario().toLocalTime().isAfter(itemEscala.getHrInicial())) {
							listaFinal.add(adicionaLista(true, datahora.horaEmString(itemEscala.getHrInicial()),
									datahora.horaEmString(listAgendamentos.get(idx).getHorario().toLocalTime())));
							// 09:00 as 10:00 ocupado A1I a A1F not free
							listaFinal.add(adicionaLista(false,
									datahora.horaEmString(listAgendamentos.get(idx).getHorario().toLocalTime()),
									datahora.horaEmString(listAgendamentos.get(idx).getHorarioFim().toLocalTime())));
						} else {
							// A1I a A1F not free
							listaFinal.add(adicionaLista(false,
									datahora.horaEmString(listAgendamentos.get(idx).getHorario().toLocalTime()),
									datahora.horaEmString(listAgendamentos.get(idx).getHorarioFim().toLocalTime())));
						}
					} else {
						// A2I=A1F
						if (listAgendamentos.get(idx).getHorario()
								.equals(listAgendamentos.get(idx - 1).getHorarioFim())) {
							listaFinal.add(adicionaLista(false,
									datahora.horaEmString(listAgendamentos.get(idx).getHorario().toLocalTime()),
									datahora.horaEmString(listAgendamentos.get(idx).getHorarioFim().toLocalTime())));
							// EF>AF
//							if (idx == (listAgendamentos.size() - 1) && itemEscala.getHrFinal()
//									.isAfter(listAgendamentos.get(idx).getHorarioFim().toLocalTime())) {
//								listaFinal.add(adicionaLista(true,
//										datahora.horaEmString(listAgendamentos.get(idx).getHorarioFim().toLocalTime()),
//										datahora.horaEmString(itemEscala.getHrFinal())));
//							}
						} else {
							listaFinal.add(adicionaLista(true,
									datahora.horaEmString(listAgendamentos.get(idx - 1).getHorarioFim().toLocalTime()),
									datahora.horaEmString(listAgendamentos.get(idx).getHorario().toLocalTime())));
							listaFinal.add(adicionaLista(false,
									datahora.horaEmString(listAgendamentos.get(idx).getHorario().toLocalTime()),
									datahora.horaEmString(listAgendamentos.get(idx).getHorarioFim().toLocalTime())));
						}
					}
					if (idx == (listAgendamentos.size() - 1) && itemEscala.getHrFinal()
							.isAfter(listAgendamentos.get(idx).getHorarioFim().toLocalTime())) {
						System.out.println("-------- index na condiçao final " + idx);

						listaFinal.add(adicionaLista(true,
								datahora.horaEmString(listAgendamentos.get(idx).getHorarioFim().toLocalTime()),
								datahora.horaEmString(itemEscala.getHrFinal())));
					}
				});
			} else {
				listaFinal.add(adicionaLista(true, datahora.horaEmString(itemEscala.getHrInicial()),
						datahora.horaEmString(itemEscala.getHrFinal())));
			}
		}
		return listaFinal;
	}

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
		return repositorio.findAllByClienteId(idCliente);
	}

	public List<Agendamento> listarPorServico(long idServicoFuncionario) {
		verificaServicoFuncionario(idServicoFuncionario);
		return repositorio.findByServicoFuncionarioIdOrderByHorarioAsc(idServicoFuncionario);
	}

	@Override
	protected void verificaSalvar(AgendamentoDTO dto) {
		verificaCliente(dto.getCliente().getId());
		verificaServicoFuncionario(dto.getServicoFuncionario().getId());
		verificaPreSave(dto);
		if (dto.getStatus() == null) {
			dto.setStatus(StatusAgendamento.PENDENTE);
		}
	}

	@Override
	protected Agendamento verificUpdate(AgendamentoDTO dto) {
		Agendamento agendamento = verificaExixte(dto.getId()).get();
		verificaCliente(dto.getCliente().getId());
		verificaServicoFuncionario(dto.getServicoFuncionario().getId());
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
		return new Agendamento(verificaCliente(dto.getCliente().getId()),
				verificaServicoFuncionario(dto.getServicoFuncionario().getId()),
				datahora.stringemDateTime(dto.getHorarioInicio()), datahora.stringemDateTime(dto.getHorarioFim()),
				dto.getNotificacao(), dto.getObs(), dto.getStatus());
	}

	@Override
	protected Agendamento transformaEditar(AgendamentoDTO dto) {
		return new Agendamento(dto.getId(), verificaCliente(dto.getCliente().getId()),
				verificaServicoFuncionario(dto.getServicoFuncionario().getId()),
				datahora.stringemDateTime(dto.getHorarioInicio()), datahora.stringemDateTime(dto.getHorarioFim()),
				dto.getNotificacao(), dto.getObs(), dto.getStatus());
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
		// if (verificaDisponibilidadeGeral(dto) == true) {
		if (verificaEscala(dto) == true) {
			verificaHorario(dto);
		}
		// }
	}

	private boolean verificaEscala(AgendamentoDTO dto) {
		DayOfWeek day = datahora.stringemDateTime(dto.getHorarioInicio()).getDayOfWeek();
		System.out.println("--day " + day);

		LocalTime hrInicial = datahora.stringemDateTime(dto.getHorarioInicio()).toLocalTime();
		LocalTime hrFinal = datahora.stringemDateTime(dto.getHorarioInicio()).toLocalTime();
		Escala escala = escalaRepository
				.findByServicoFuncionarioIdAndDiaSemana(dto.getServicoFuncionario().getId(),
						day.getDisplayName(TextStyle.FULL, new Locale("pt")))
				.orElseThrow(() -> new ResourceNotFoundException("Não Há Escala"));
		ItemEscala itemEscala = itemEscalaRepository.escala(escala.getId(), hrInicial, hrFinal)
				.orElseThrow(() -> new ResourceNotFoundException("Horario fora de escala disponivel"));
		return true;
	}

	private void verificaHorario(AgendamentoDTO dto) {
		int qtd = repositorio.countChoques(datahora.stringemDateTime(dto.getHorarioInicio()),
				datahora.stringemDateTime(dto.getHorarioFim()), dto.getServicoFuncionario().getId());
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
