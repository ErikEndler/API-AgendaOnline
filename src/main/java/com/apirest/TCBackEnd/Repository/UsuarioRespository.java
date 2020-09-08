package com.apirest.TCBackEnd.Repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.apirest.TCBackEnd.Models.Usuario;

public interface UsuarioRespository extends CrudRepository<Usuario, Long> {
	Optional<Usuario> findByCpf(String cpf);

}
