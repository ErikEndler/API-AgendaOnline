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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.apirest.TCBackEnd.DTO.AgendamentoDTO;
import com.apirest.TCBackEnd.Models.Agendamento;
import com.apirest.TCBackEnd.Models.Escala;
import com.apirest.TCBackEnd.Models.ItemEscala;
import com.apirest.TCBackEnd.Models.ServicoFuncionario;
import com.apirest.TCBackEnd.Models.TimeLine;
import com.apirest.TCBackEnd.Models.Usuario;
import com.apirest.TCBackEnd.Notification.NotificationDispatcher;
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

	private static final Logger LOGGER = LoggerFactory.getLogger(NotificationDispatcher.class);

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
	@Autowired
	NotificationDispatcher NotificationDispatcher;

	public void atualizaScore(long idCliente) {
		List<Agendamento> agendamentos = repositorio.findByClienteIdAndStatusIs(idCliente, StatusAgendamento.FALTOU);
		long qtd = agendamentos.stream().count();
		Usuario cliente = usuarioRepository.findById(idCliente)
				.orElseThrow(() -> new ResourceNotFoundException("Usuario não encontrado! id=" + idCliente));
		cliente.setScore((int) qtd);
		usuarioRepository.save(cliente);
	}

	// cancela pedidos pendentes que tem datra inferior a atual
	@Scheduled(cron = "0 0 8/1 * * * ", zone = "America/Sao_Paulo")
	public void cancelamentoAutomaticoAgendamento() {
		List<Agendamento> agendamentos = repositorio.findByStatus(StatusAgendamento.PENDENTE);

		agendamentos.stream().filter(agd -> agd.getHorario().toLocalDate().isBefore(LocalDate.now()))// filtra
				.forEach(agd -> {
					agd.setStatus(StatusAgendamento.CANCELADO);
					repositorio.save(agd);
				});
		LOGGER.info("cancelamentoAutomaticoAgendamento() executado!");
		// System.out.println("EXECUTOU CRON");
	}

	// atribui NAOTENDIDO a agendamentos confirmados com data inferior a atual
	@Scheduled(cron = "0 0 8/1 * * * ", zone = "America/Sao_Paulo")
	public void naoAtendidoAutomatico() {
		List<Agendamento> agendamentos = repositorio.findByStatus(StatusAgendamento.AGENDADO);

		agendamentos.stream().filter(agd -> agd.getHorario().toLocalDate().isBefore(LocalDate.now()))// filtra
				.forEach(agd -> {
					agd.setStatus(StatusAgendamento.NAOATENDIDO);
					repositorio.save(agd);
				});
		LOGGER.info("naoAtendidoAutomatico() executado!");
		// System.out.println("EXECUTOU CRON");
	}

	// retorna lista dos agendamentos que conflitam com um agendamento
	public List<Agendamento> listarAgendamentosConflitantes(long id) {
		Optional<Agendamento> agendamento = repositorio.findById(id);
		List<Agendamento> lista = new ArrayList<>();
		if (agendamento.isPresent()) {
			lista = repositorio.countChoques(agendamento.get().getHorario(), agendamento.get().getHorarioFim(),
					agendamento.get().getServicoFuncionario().getFuncionario().getId(), 0);
			// .stream().filter(a -> a.getId() != id).collect(Collectors.toList());
			return lista;
		}
		return lista;
	}

	// Lista os agendamentos por status de um funcioanrio
	public List<Agendamento> listarAgendamentosPorStatus(long idfuncionario, StatusAgendamento status) {
		List<Agendamento> lista = repositorio
				.findByServicoFuncionarioFuncionarioIdAndStatusOrderByHorarioDesc(idfuncionario, status);
		return lista;
	}

	// Lista os atendimentos atendiveis do dia de um funcionario
	public List<Agendamento> listarAgendamentosAtendiveisDia(long id) {
		List<Agendamento> lista = repositorio.listarAgendamentosAtendiveis(LocalDate.now(), id);
		return lista;
	}

	// lista os status de agendamento do sistema
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
		List<Agendamento> listAgendamentos = repositorio.agendamentosConfirmadosDia(datahora.stringEmData(data),
				servicoFuncionario.getFuncionario().getId());

		List<TimeLine> listaFinal = new ArrayList<>();
		if (listItemEscala.size() > 0) {
			ItemEscala itemEscala = listItemEscala.get(0);
			if (listAgendamentos.size() > 0) {
				IntStream.range(0, listAgendamentos.size()).forEach(idx -> {
					// para o primeiro indice
					if (idx == 0) {
						// if T1>T2
						if (listAgendamentos.get(idx).getHorario().toLocalTime().isAfter(itemEscala.getHrInicial())
								&& listAgendamentos.get(idx).getStatus() == StatusAgendamento.AGENDADO) {
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
						if (listAgendamentos.get(idx).getHorario().equals(listAgendamentos.get(idx - 1).getHorarioFim())
								&& listAgendamentos.get(idx).getStatus() == StatusAgendamento.AGENDADO) {
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
					if (idx == (listAgendamentos.size() - 1)
							&& itemEscala.getHrFinal().isAfter(listAgendamentos.get(idx).getHorarioFim().toLocalTime())
							&& listAgendamentos.get(idx).getStatus() == StatusAgendamento.AGENDADO) {
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

	// lista agendamentos de um cliente
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
		verificaPreUpdate(dto);
		Agendamento agendamento = verificaExixte(dto.getId()).get();
		verificaCliente(dto.getCliente().getId());
		verificaServicoFuncionario(dto.getServicoFuncionario().getId());
		verificaPreEdit(dto);
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
		if (retorno.getStatus() == StatusAgendamento.FALTOU) {
			atualizaScore(retorno.getCliente().getId());
		}
		enviarNotificacao(retorno);
	}

	// lista todos horarios do dia
	private void listar(AgendamentoDTO dto) {
		LocalDate data = datahora.stringemDateTime(dto.getHorarioInicio()).toLocalDate();

		List<Agendamento> agendamentos = repositorio.agendamentosDia(data);
	}

	private void verificaPreSave(AgendamentoDTO dto) {
		if (verificaEscala(dto) == true) {
			verificaHorario(dto);
		}
	}

	private void verificaPreUpdate(AgendamentoDTO dto) {
		if (verificaEscala(dto) == true) {
			verificaHorarioEdit(dto);
		}
	}

	private void verificaPreEdit(AgendamentoDTO dto) {
		// if (verificaDisponibilidadeGeral(dto) == true) {
		if (verificaEscala(dto) == true) {
			verificaHorarioEdit(dto);
		}
		// }
	}

	private boolean verificaEscala(AgendamentoDTO dto) {
		DayOfWeek day = datahora.stringemDateTime(dto.getHorarioInicio()).getDayOfWeek();
		LocalTime hrInicial = datahora.stringemDateTime(dto.getHorarioInicio()).toLocalTime();
		LocalTime hrFinal = datahora.stringemDateTime(dto.getHorarioInicio()).toLocalTime();
		Escala escala = escalaRepository
				.findByServicoFuncionarioIdAndDiaSemana(dto.getServicoFuncionario().getId(),
						day.getDisplayName(TextStyle.FULL, new Locale("pt")))
				.orElseThrow(() -> new ResourceNotFoundException("Não Há Escala"));
		itemEscalaRepository.escala(escala.getId(), hrInicial, hrFinal)
				.orElseThrow(() -> new ResourceNotFoundException("Horario fora de escala disponivel"));
		return true;
	}

	private void verificaHorario(AgendamentoDTO dto) {
		int qtd = repositorio.countChoques(datahora.stringemDateTime(dto.getHorarioInicio()),
				datahora.stringemDateTime(dto.getHorarioFim()), dto.getServicoFuncionario().getFuncionario().getId(), 4)
				.size();
		if (qtd > 0) {
			throw new ResourceNotFoundException("Horario do funcionario ja ocupado");
		}
	}

	private void verificaHorarioEdit(AgendamentoDTO dto) {
		int qtd = repositorio.countChoquesEdit(datahora.stringemDateTime(dto.getHorarioInicio()),
				datahora.stringemDateTime(dto.getHorarioFim()), dto.getServicoFuncionario().getFuncionario().getId(),
				dto.getId()).size();
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

	@Override
	public Agendamento editar(AgendamentoDTO dto) {
		verificUpdate(dto);
		Agendamento agendamento = repositorio.save(transformaEditar(dto));
		if (agendamento.getStatus() == StatusAgendamento.FALTOU) {
			atualizaScore(agendamento.getCliente().getId());
		}
		enviarNotificacao(agendamento);
		return agendamento;
	}

	private void enviarNotificacao(Agendamento agendamento) {
		if (agendamento.getStatus() == StatusAgendamento.PENDENTE) {
			NotificationDispatcher.enviarMSG(SecurityContextHolder.getContext().getAuthentication().getName(),
					"SINO#" + agendamento.getId() + "#Novo Agendamento pendente");
			NotificationDispatcher.enviarMSG(agendamento.getServicoFuncionario().getFuncionario().getCpf(),
					"SINO#" + agendamento.getId() + "#Novo Agendamento pendente");
		}
		if (agendamento.getStatus() == StatusAgendamento.AGENDADO) {
			NotificationDispatcher.enviarMSG(agendamento.getCliente().getCpf(),
					"SINO#" + agendamento.getId() + "#Novo Agendamento Confirmado");
		}
	}

}
