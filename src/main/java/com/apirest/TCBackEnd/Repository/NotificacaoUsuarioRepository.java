package com.apirest.TCBackEnd.Repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.apirest.TCBackEnd.Models.NotificacaoUsuario;

public interface NotificacaoUsuarioRepository extends CrudRepository<NotificacaoUsuario, Long> {

	Optional<NotificacaoUsuario> findByUsuarioId(long id);

}
