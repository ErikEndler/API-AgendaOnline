package com.apirest.TCBackEnd.Repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.apirest.TCBackEnd.Models.Escala;

public interface EscalaRepository extends CrudRepository<Escala, Long>{
	Iterable<Escala> findAllByServicoFuncionarioId(long servico);
	Optional<Escala>  findByDiaSemana(String string);
	Optional<Escala>  findByServicoFuncionarioIdAndDiaSemana(long id,String string);

}
