package com.apirest.TCBackEnd.Repository;

import org.springframework.data.repository.CrudRepository;

import com.apirest.TCBackEnd.Models.Escala;

public interface EscalaRepository extends CrudRepository<Escala, Long>{
	Iterable<Escala> findAllByServico(long servico);
}
