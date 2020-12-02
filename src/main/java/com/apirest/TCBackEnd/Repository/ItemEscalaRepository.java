package com.apirest.TCBackEnd.Repository;

import java.time.LocalTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.apirest.TCBackEnd.Models.ItemEscala;

public interface ItemEscalaRepository extends CrudRepository<ItemEscala, Long> {
	Iterable<ItemEscala> findAllByEscalaId(long id);

	Optional<ItemEscala> findByEscalaIdAndHrInicialAndHrFinal(long escala_id, LocalTime hr_inicial, LocalTime hr_final);

	@Query(value = "select Item_escala.* from item_escala " + "where item_escala.escala_id = ?1 "
			+ "and (?2 >= hrInicial and ?3 =< hrFinal)", nativeQuery = true)
	Optional<ItemEscala> escala(long id, LocalTime HrInicial, LocalTime HrFinal);

	@Query(value = "select count(*) from item_escala "
			+ "where (hrInicial between ?1 and ?2  and hrFinal between ?1 and ?2 limit 1)", nativeQuery = true)
	int escalaByHrInicialAndHrfinal(LocalTime HrInicial, LocalTime HrFinal);
}
