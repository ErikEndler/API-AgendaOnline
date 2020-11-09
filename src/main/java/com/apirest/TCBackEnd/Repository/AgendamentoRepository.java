package com.apirest.TCBackEnd.Repository;

import org.springframework.data.repository.CrudRepository;

import com.apirest.TCBackEnd.Models.Agendamento;

public interface AgendamentoRepository extends CrudRepository<Agendamento, Long> {
	Iterable<Agendamento> findAllByCliente(long id);
	Iterable<Agendamento> findAllByServico(long id);

}
