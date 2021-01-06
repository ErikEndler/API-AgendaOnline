package com.apirest.TCBackEnd.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.apirest.TCBackEnd.Models.Agendamento;
import com.apirest.TCBackEnd.Models.ItemEscala;

public interface AgendamentoRepository extends CrudRepository<Agendamento, Long> {
	Iterable<Agendamento> findAllByCliente(long id);

	Iterable<Agendamento> findAllByServicoFuncionarioOrderByHorario(long id);

	@Query(value = "select count(*) from agendamento where ?1 < horario_fim and ?2 > horario", nativeQuery = true)
	int qtdSimultaneos(LocalDateTime dataInicial, LocalDateTime datafinal);

	@Query(value = "select count(*) from agendamento"
			+ " where servico_funcionario_id = ?3 and ?1 < horario_fim and ?2 > horario", nativeQuery = true)
	int countChoques(LocalDateTime dataInicial, LocalDateTime datafinal, long servicoFuncionario_id);

	@Query(value = "select item_escala.* from item_escala " + "join escala on escala.id = item_escala.escala_id "
			+ "join servico on servico.id = escala.servico_id "
			+ "where escala.dia_semana= ?1 and servico.id= ?2", nativeQuery = true)
	List<ItemEscala> servicoEscalaDia(String dia_semana, long servico_id);

	// lista agendamentos geral do dia
	@Query(value = "select * from agendamento where horario = ?1 order by horario ", nativeQuery = true)
	List<Agendamento> agendamentosDia(LocalDate data);

	// lista de agendamentos por dia e por funcionrio
	@Query(value = "select * from agendamento "
			+ "join servico_funcionario on servico_funcionario.id=agendamento.servico_funcionario_id"
			+ " where date_trunc('day', horario)= ?1 and servico_funcionario.funcionario_id=?2 order by horario", nativeQuery = true)

	List<Agendamento> findByHorarioAndServicoFuncionario(LocalDate data, long idFuncionario);

}
