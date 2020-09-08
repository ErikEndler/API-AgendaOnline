package com.apirest.TCBackEnd.Repository;

import org.springframework.data.repository.CrudRepository;

import com.apirest.TCBackEnd.Models.Usuario;

public interface UsuarioRespository extends CrudRepository<Usuario, Long> {
	Usuario findByCpf(String cpf);

}
