package com.apirest.TCBackEnd.Repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.apirest.TCBackEnd.Models.Role;

public interface RoleRespository extends CrudRepository<Role, Long>{
	Optional<Role> findByNameRole(String role);

}
