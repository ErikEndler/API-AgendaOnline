package com.apirest.TCBackEnd.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.apirest.TCBackEnd.Models.Atendimento;

public interface AtendimentoRepository extends CrudRepository<Atendimento, Long>{
	List<Atendimento> findByFuncionarioId(long id);

}
