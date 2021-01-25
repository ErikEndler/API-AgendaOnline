package com.apirest.TCBackEnd.Repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.apirest.TCBackEnd.Models.Avaliacao;

public interface AvaliacaoRepository extends CrudRepository<Avaliacao, Long> {

	Optional<Avaliacao> findByAtendimentoId(long id);

}
