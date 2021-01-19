package com.apirest.TCBackEnd.Endpoint;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apirest.TCBackEnd.Controle.UsuarioControle;
import com.apirest.TCBackEnd.DTO.UsuarioDTO;
import com.apirest.TCBackEnd.Models.Usuario;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api/usuario")
@Api(value = "API REST Usuario")
@CrossOrigin(origins = "*")

public class UsuarioEndPoint {

	@Autowired
	UsuarioControle usuarioControle;

	@ApiOperation(value = "Retorna uma lista de Usuarios")
	@GetMapping("")
	// @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> listarTodos() {
		return new ResponseEntity<>(UsuarioDTO.listarResposta(usuarioControle.listarTodos()), HttpStatus.OK);
	}

	@ApiOperation(value = "Retorna um Usuario unico pelo ID")
	@GetMapping("/{id}")
	public ResponseEntity<?> listar(@PathVariable(value = "id") long id) {
		Optional<Usuario> user = usuarioControle.listar(id);
		return new ResponseEntity<>(UsuarioDTO.usuarioResposta(user.get()), HttpStatus.OK);
	}

	@ApiOperation(value = "Retorna um Usuario unico pelo CPF")
	@GetMapping("/cpf/{cpf}")
	public ResponseEntity<?> listarCpf(@PathVariable(value = "cpf") long id) {
		Optional<Usuario> user = usuarioControle.listar(id);
		return new ResponseEntity<>(UsuarioDTO.usuarioResposta(user.get()), HttpStatus.OK);
	}

	@ApiOperation(value = "Salva um Usuario")
	@PostMapping("")
	public ResponseEntity<?> salvar(@RequestBody @Valid UsuarioDTO usuarioDTO) {
		Usuario usuario = usuarioControle.salvar(usuarioDTO);
		return new ResponseEntity<>(UsuarioDTO.usuarioResposta(usuario), HttpStatus.CREATED);
	}

	@ApiOperation(value = "Edita um Usuario")
	@PutMapping("")
	public ResponseEntity<?> editar(@RequestBody @Valid UsuarioDTO usuarioDTO) {
		Usuario usuario = usuarioControle.editar(usuarioDTO);
		return new ResponseEntity<>(UsuarioDTO.usuarioResposta(usuario), HttpStatus.ACCEPTED);
	}

	@ApiOperation(value = "Deleta um Usuario por Id")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable(value = "id") long id) {
		usuarioControle.deletarById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ApiOperation(value = "Retorna lista de funcionarios")
	@GetMapping("/funcionarios")
	public ResponseEntity<?> listarFuncionarios() {
		List<Usuario> lista = usuarioControle.listarNaoClientes();
		return new ResponseEntity<>(UsuarioDTO.listarResposta(lista), HttpStatus.OK);
	}

	@ApiOperation(value = "Redefine a senha do usuario")
	@PostMapping("/recuperarSenha")
	public ResponseEntity<?> redefinirSenha(@RequestBody Map<String, String> requestParams) {
		//usuarioControle.trocarSenha(cpf, email);
	    String cpf = requestParams.get("cpf");
	    String email = requestParams.get("email");


		System.out.println("cpf :"+cpf);
		System.out.println("email : "+email);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

}
