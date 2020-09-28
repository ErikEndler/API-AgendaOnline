package com.apirest.TCBackEnd.Config;

import static com.apirest.TCBackEnd.Config.JwtConfig.HEADER_STRING;
import static com.apirest.TCBackEnd.Config.JwtConfig.SECRET_KEY;
import static com.apirest.TCBackEnd.Config.JwtConfig.TOKEN_PREFIX;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Jwts;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	private MyUserDetailsService myUserDetailsService;

	@Autowired
	public JwtAuthorizationFilter(AuthenticationManager authenticationManager,
			MyUserDetailsService myUserDetailsService) {
		super(authenticationManager);
		this.myUserDetailsService = myUserDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String header = request.getHeader(HEADER_STRING);
		if (header == null || !header.startsWith(TOKEN_PREFIX)) {
			chain.doFilter(request, response);
			return;
		}
		UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(request);
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		chain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthenticationToken(HttpServletRequest request) {
		String token = request.getHeader(HEADER_STRING);
		if (token == null)
			throw new IllegalStateException("ERRO de autorização");
		String username = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
				.getBody().getSubject();
		UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);

		return username != null
				? new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities())
				: null;
	}

}
