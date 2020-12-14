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

import com.apirest.TCBackEnd.Controle.AgendamentoControle;
import com.apirest.TCBackEnd.DTO.AgendamentoDTO;
import com.apirest.TCBackEnd.Models.Agendamento;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api/agendamento")
@Api(value = "API REST Agendamento")
@CrossOrigin(origins = "*")
public class AgendamentoEndPoint {

	@Autowired
	AgendamentoControle agendamentoControle;

	@ApiOperation(value = "Retorna uma lista de Agendamentos")
	@GetMapping("")
	public ResponseEntity<?> listarTodos() {
		return new ResponseEntity<>(AgendamentoDTO.listarResposta(agendamentoControle.listarTodos()), HttpStatus.OK);
	}

	@ApiOperation(value = "Retorna uma lista de Agendamentos por cliente")
	@GetMapping("/cliente/{id}")
	public ResponseEntity<?> listarPorCliente(@PathVariable(value = "id") long id) {
		return new ResponseEntity<>(AgendamentoDTO.listarResposta(agendamentoControle.listarPorCliente(id)),
				HttpStatus.OK);
	}
	@ApiOperation(value = "Retorna uma lista de Agendamentos por Servico")
	@GetMapping("/servico/{id}")
	public ResponseEntity<?> listarPorServico(@PathVariable(value = "id") long id) {
		return new ResponseEntity<>(AgendamentoDTO.listarResposta(agendamentoControle.listarPorServico(id)),
				HttpStatus.OK);
	}
	
	@ApiOperation(value = "Retorna uma lista de Agendamentos por uma data")
	@GetMapping("/data/{data}")
	public ResponseEntity<?> listarAgendamentosDoDia(@PathVariable(value = "data") String data) {
		return new ResponseEntity<>(AgendamentoDTO.listarResposta(agendamentoControle.listaAgendamentosDia(data)),
				HttpStatus.OK);
	}

	@ApiOperation(value = "Retorna um Agendamento unico pelo ID")
	@GetMapping("/{id}")
	public ResponseEntity<?> listar(@PathVariable(value = "id") long id) {
		Optional<Agendamento> categoria = agendamentoControle.listar(id);
		return new ResponseEntity<>(AgendamentoDTO.agendamentoResposta(categoria.get()), HttpStatus.OK);
	}

	@ApiOperation(value = "Salva uma Agendamento")
	@PostMapping("")
	public ResponseEntity<?> salvar(@RequestBody @Valid AgendamentoDTO agendamentoDTO) {
		Agendamento agendamento = agendamentoControle.salvar(agendamentoDTO);
		return new ResponseEntity<>(AgendamentoDTO.agendamentoResposta(agendamento), HttpStatus.CREATED);
	}

	@ApiOperation(value = "Edita uma Agendamento")
	@PutMapping("")
	public ResponseEntity<?> editar(@RequestBody @Valid AgendamentoDTO agendamentoDTO) {
		Agendamento agendamento = agendamentoControle.editar(agendamentoDTO);
		return new ResponseEntity<>(AgendamentoDTO.agendamentoResposta(agendamento), HttpStatus.ACCEPTED);
	}

	@ApiOperation(value = "Deleta um Agendamento por Id")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable(value = "id") long id) {
		agendamentoControle.deletarById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
