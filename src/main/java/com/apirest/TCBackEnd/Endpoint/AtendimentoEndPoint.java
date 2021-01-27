package com.apirest.TCBackEnd.Endpoint;

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
import org.springframework.web.bind.annotation.RestController;

import com.apirest.TCBackEnd.Controle.AtendimentoControle;
import com.apirest.TCBackEnd.DTO.AtendimentoDTO;
import com.apirest.TCBackEnd.Models.Atendimento;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api/atendimento")
@Api(value = "API REST Atendimento")
@CrossOrigin(origins = "*")
public class AtendimentoEndPoint {

	@Autowired
	AtendimentoControle atendimentoControle;

	@ApiOperation(value = "Retorna uma lista de Atendimentos")
	@GetMapping("")
	public ResponseEntity<?> listarTodos() {
		return new ResponseEntity<>(AtendimentoDTO.listarResposta(atendimentoControle.listarTodos()), HttpStatus.OK);
	}

	@ApiOperation(value = "Retorna um atendimento unico pelo ID")
	@GetMapping("/{id}")
	public ResponseEntity<?> listar(@PathVariable(value = "id") long id) {
		Optional<Atendimento> atendimento = atendimentoControle.listar(id);
		return new ResponseEntity<>(AtendimentoDTO.atendimentoResposta(atendimento.get()), HttpStatus.OK);
	}

	@ApiOperation(value = "Retorna uma lista de Atendimentos por Funcionario")
	@GetMapping("/funcionario/{id}")
	public ResponseEntity<?> listarPorFuncionario(@PathVariable(value = "id") long id) {
		return new ResponseEntity<>(AtendimentoDTO.listarResposta(atendimentoControle.listarPorFuncionario(id)),
				HttpStatus.OK);
	}

	@ApiOperation(value = "Retorna um Atendimentos de um Agendamento")
	@GetMapping("/agendamento/{id}")
	public ResponseEntity<?> listarPorAgendamento(@PathVariable(value = "id") long id) {
		return new ResponseEntity<>(AtendimentoDTO.atendimentoResposta(atendimentoControle.findByAgendamento(id)),
				HttpStatus.OK);
	}

	@ApiOperation(value = "Salva um Atendimento")
	@PostMapping("")
	public ResponseEntity<?> salvar(@RequestBody @Valid AtendimentoDTO atendimentoDTO) {
		Atendimento atendimento = atendimentoControle.salvar(atendimentoDTO);
		return new ResponseEntity<>(AtendimentoDTO.atendimentoResposta(atendimento), HttpStatus.CREATED);
	}

	@ApiOperation(value = "Edita um Atendimento")
	@PutMapping("")
	public ResponseEntity<?> editar(@RequestBody @Valid AtendimentoDTO atendimentoDTO) {
		Atendimento atendimento = atendimentoControle.salvar(atendimentoDTO);
		return new ResponseEntity<>(AtendimentoDTO.atendimentoResposta(atendimento), HttpStatus.ACCEPTED);
	}

	@ApiOperation(value = "Deleta um Atendimento por Id")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable(value = "id") long id) {
		atendimentoControle.deletarById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
