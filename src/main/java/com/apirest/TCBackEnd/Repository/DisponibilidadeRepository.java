package com.apirest.TCBackEnd.Repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.apirest.TCBackEnd.Models.Disponibilidade;

public interface DisponibilidadeRepository extends CrudRepository<Disponibilidade, Long> {
	
	Optional<Disponibilidade> findByNome(String string);

}
