package com.apirest.TCBackEnd.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.apirest.TCBackEnd.Models.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
	Optional<Usuario> findByCpf(String cpf);
	
	@Query(value = "select * from usuario where role_id <>2", nativeQuery = true)
	List<Usuario> listarFuncionarios();

}
