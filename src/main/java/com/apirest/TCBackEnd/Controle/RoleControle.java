package com.apirest.TCBackEnd.Controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apirest.TCBackEnd.Models.Role;
import com.apirest.TCBackEnd.Repository.RoleRespository;

@Service
public class RoleControle {

	@Autowired
	RoleRespository roelRespository;

	// verifica a contagem de roles
	//realiza verificação sempre q aplicaçao inicia
	//@EventListener(ContextRefreshedEvent.class)
	public void verificaRoles() {
		System.out.println("---função inicial, verifica se há ROLES no sistema");
		if (roelRespository.count() == 0) {
			System.out.println("Sistema não possui Roles cadastradas !!!");
			System.out.println("Iniciando Incersao de roles default....");
			cadastrarRoles();
		}
		System.out.println("Sistema possui Roles cadastradas !");
	}

	// adiciona as roles default do sistema
	public void cadastrarRoles() {
		Role role = new Role();
		role.setNameRole("ROLE_ADMIN");
		roelRespository.save(role);
		Role role1 = new Role();
		role1.setNameRole("ROLE_USER");
		roelRespository.save(role1);
		Role role2 = new Role();
		role2.setNameRole("ROLE_FUNCIONARIO");
		roelRespository.save(role2);
		System.out.println("Roles Inseridas !!");
	}
}
