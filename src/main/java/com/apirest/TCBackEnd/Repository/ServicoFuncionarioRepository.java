package com.apirest.TCBackEnd.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.apirest.TCBackEnd.Models.ServicoFuncionario;

public interface ServicoFuncionarioRepository extends CrudRepository<ServicoFuncionario, Long>{
	// retorna buscando por funcionari oe servico
	//@Query(value = "select * from servico_funcionario "+"where funcionario_id = ?1 and servico_id = ?2", nativeQuery = true)
	Optional<ServicoFuncionario> findByFuncionarioIdAndServicoId(long funcionarioId,long servicoId);
	
	
	List<ServicoFuncionario> findByFuncionarioId(long idFuncionario);

}
