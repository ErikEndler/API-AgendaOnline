package com.apirest.TCBackEnd.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.apirest.TCBackEnd.Models.Avaliacao;

public interface AvaliacaoRepository extends CrudRepository<Avaliacao, Long> {

	Optional<Avaliacao> findByAtendimentoId(long id);

	// lista de avaliaçoes de um funcionario
	List<Avaliacao> findByAtendimentoFuncionarioId(long idFuncionario);

	// lista de avaliaçoes de um cliente
	List<Avaliacao> findByAtendimentoAgendamentoClienteId(long idCliente);

}
