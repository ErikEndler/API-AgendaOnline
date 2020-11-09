package com.apirest.TCBackEnd.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.apirest.TCBackEnd.Repository.UsuarioRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private MyUserDetailsService myUserDetailsService;
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	};

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().cors().and()  //	
				
				.sessionManagement() //
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS) //
				.and() //
				.addFilter(new JwtAuthenticationFilter(authenticationManager(), usuarioRepository)) //
				.addFilter(new JwtAuthorizationFilter(authenticationManager(), myUserDetailsService))
				.authorizeRequests() //
				.antMatchers(publicEndPoints()).permitAll() //
				.antMatchers(HttpMethod.GET).permitAll()
				.anyRequest().authenticated();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder builder) throws Exception {
		builder.userDetailsService(myUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

	private String[] publicEndPoints() {
		return new String[] { "/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**", "/error/**",
				"/h2-console/**", "/resources/**", "/v2/rest-docs", "/v2/api-docs", "/swagger-resources/configuration",
				"/swagger-resources", "/**.html", "/webjars/**", "/login", "/csrf", "/" };
	}

}
