package com.apirest.TCBackEnd.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.apirest.TCBackEnd.Models.Usuario;
import com.apirest.TCBackEnd.Repository.UsuarioRepository;

@Component
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByCpf(login).get();
		if (usuario == null) {
			throw new UsernameNotFoundException("Usuario não encontrado!");
		}
		//System.out.println("Autorizaçoes:" + usuario.getAuthorities().toArray().toString());

		//System.out.println("USUARIO: " + usuario.getCpf() + "  -  " + usuario.getSenha());

		//System.out.println("IMPRIMIR: ");

		User user = new User(usuario.getCpf(), usuario.getSenha(), true, true, true, true, usuario.getAuthorities());
		//System.out.println("NEW USER- USERNAME: " + (user.getUsername()));

		return new User(usuario.getCpf(), usuario.getSenha(), usuario.getAuthorities());
	}

}
