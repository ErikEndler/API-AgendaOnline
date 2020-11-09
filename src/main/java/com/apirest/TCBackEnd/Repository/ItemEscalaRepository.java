package com.apirest.TCBackEnd.Repository;

import org.springframework.data.repository.CrudRepository;

import com.apirest.TCBackEnd.Models.ItemEscala;

public interface ItemEscalaRepository extends CrudRepository<ItemEscala, Long>{
	Iterable<ItemEscala> findAllByEscala(long id);
}
