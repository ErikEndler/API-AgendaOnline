package com.apirest.TCBackEnd.Config;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import static com.apirest.TCBackEnd.Config.JwtConfig.*;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.apirest.TCBackEnd.Models.Usuario;
import com.apirest.TCBackEnd.Repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Transactional

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;

	private final UsuarioRepository usuarioRepository;

	@Autowired
	public JwtAuthenticationFilter(AuthenticationManager authenticationManager, UsuarioRepository usuarioRespository) {
		this.authenticationManager = authenticationManager;
		this.usuarioRepository = usuarioRespository;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			Usuario user = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
			// user=getfull(user);
			return this.authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		String token = null;
		Optional<Usuario> usuario = usuarioRepository.findByCpf(authResult.getName());

		token = Jwts.builder() // -
				// .setSubject(authResult.getName())// -
				// .claim("authorities", authResult.getAuthorities()) // --
				.claim("nome", usuario.get().getNome()) // --
				.claim("role", usuario.get().getRole().getNameRole()) // --
				.claim("cpf", usuario.get().getCpf()) // --
				.claim("id", usuario.get().getId()) // --
				// .setPayload(usuario.toString())//
				.setIssuedAt(new Date())// ---
				.setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
		response.getWriter().append("{" + "\"Authorization\": \"Bearer " + token + "\"}");

		response.addHeader("Authorization", "Bearer " + token);
	}

}
