package com.apirest.TCBackEnd.Repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.apirest.TCBackEnd.Models.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
	Optional<Usuario> findByCpf(String cpf);

}
