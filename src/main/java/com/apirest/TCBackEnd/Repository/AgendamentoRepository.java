package com.apirest.TCBackEnd.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.apirest.TCBackEnd.Models.Agendamento;
import com.apirest.TCBackEnd.Models.ItemEscala;
import com.apirest.TCBackEnd.Util.StatusAgendamento;

public interface AgendamentoRepository extends CrudRepository<Agendamento, Long> {
	Iterable<Agendamento> findAllByClienteId(long id);

	List<Agendamento> findByServicoFuncionarioIdOrderByHorarioAsc(long id);

	@Query(value = "select count(*) from agendamento where ?1 < horario_fim and ?2 > horario", nativeQuery = true)
	int qtdSimultaneos(LocalDateTime dataInicial, LocalDateTime datafinal);

	// cancela os agendamentos em conflito com um agendamento em especifico
	@Query(value = " UPDATE agendamento SET status = 3 WHERE agendamento.id=( "
			+ "SELECT agendamento.id FROM agendamento "
			+ " JOIN servico_funcionario on servico_funcionario.id=agendamento.servico_funcionario_id"
			+ " WEHERE servico_funcionario.funcionario_id = ?3 and ?1 < horario_fim and ?2 > horario AND status=1 "
			+ " AND agendamento.id not in(?4))", nativeQuery = true)
	int cancelaAgendamentosConflito(LocalDateTime dataInicial, LocalDateTime datafinal, long funcionario_id,
			long idAgendamento);

	// retorna lista de ids de choque de horario por status
	@Query(value = "select agendamento.id from agendamento "
			+ " join servico_funcionario on servico_funcionario.id=agendamento.servico_funcionario_id"
			+ " where servico_funcionario.funcionario_id = ?3 and ?1 < horario_fim and ?2 > horario AND status=?4", nativeQuery = true)
	List<Integer> countChoques(LocalDateTime dataInicial, LocalDateTime datafinal, long funcionario_id, int status);

	@Query(value = "select count(*) from agendamento "
			+ " join servico_funcionario on servico_funcionario.id=agendamento.servico_funcionario_id"
			+ " where servico_funcionario.funcionario_id = ?3 and ?1 < horario_fim and ?2 > horario AND status=4 "
			+ " AND agendamento.id not in(?4)", nativeQuery = true)
	int countChoquesEdit(LocalDateTime dataInicial, LocalDateTime datafinal, long funcionario_id, long idAgendamento);

	@Query(value = "select item_escala.* from item_escala " + "join escala on escala.id = item_escala.escala_id "
			+ "join servico on servico.id = escala.servico_id "
			+ "where escala.dia_semana= ?1 and servico.id= ?2", nativeQuery = true)
	List<ItemEscala> servicoEscalaDia(String dia_semana, long servico_id);

	// lista agendamentos geral do dia
	@Query(value = "select * from agendamento where horario = ?1 order by horario ", nativeQuery = true)
	List<Agendamento> agendamentosDia(LocalDate data);

	// lista de agendamentos(todos status) por dia e por funcionario
	@Query(value = "select * from agendamento "
			+ "join servico_funcionario on servico_funcionario.id=agendamento.servico_funcionario_id"
			+ " where date_trunc('day', horario)= ?1 and servico_funcionario.funcionario_id=?2 order by horario ", nativeQuery = true)
	List<Agendamento> findByHorarioAndServicoFuncionario(LocalDate data, long idFuncionario);

	// lista de agendamentos(todos status) por dia e por funcionario
	@Query(value = "select * from agendamento "
			+ "join servico_funcionario on servico_funcionario.id=agendamento.servico_funcionario_id"
			+ " where date_trunc('day', horario)= ?1 and servico_funcionario.funcionario_id=?2 and status=4 order by horario ", nativeQuery = true)
	List<Agendamento> agendamentosConfirmadosDia(LocalDate data, long idFuncionario);

	// lista os agendamentos de um funcionario por status
	List<Agendamento> findByServicoFuncionarioFuncionarioIdAndStatusOrderByHorarioDesc(long id,
			StatusAgendamento status);

	// lista os agendamento que funcionario pode atender no dia
	@Query(value = "select * from agendamento "
			+ "join servico_funcionario on servico_funcionario.id=agendamento.servico_funcionario_id"
			+ " where date_trunc('day', horario)= ?1 and servico_funcionario.funcionario_id=?2 and status= 4"
			+ " order by horario", nativeQuery = true)
	List<Agendamento> listarAgendamentosAtendiveis(LocalDate data, long id);

}
