package com.apirest.TCBackEnd.Endpoint;

import java.util.ArrayList;
import java.util.List;
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

import com.apirest.TCBackEnd.Controle.EscalaControle;
import com.apirest.TCBackEnd.DTO.EscalaDTO;
import com.apirest.TCBackEnd.Models.Escala;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api/escala")
@Api(value = "API REST Escala")
@CrossOrigin(origins = "*")
public class EscalaEndPoint {
	@Autowired
	EscalaControle escalaControle;

	@ApiOperation(value = "Retorna uma lista de Escalas")
	@GetMapping("")
	public ResponseEntity<?> listarTodos() {
		return new ResponseEntity<>(EscalaDTO.listarResposta(escalaControle.listarTodos()), HttpStatus.OK);
	}
	
	@ApiOperation(value = "Retorna uma lista dias semana")
	@GetMapping("/dayweek")
	public ResponseEntity<?> listarDayWeek() {
		return new ResponseEntity<>(escalaControle.listaDayWeek(), HttpStatus.OK);
	}
	

	@ApiOperation(value = "Retorna uma Escala unico pelo ID")
	@GetMapping("/{id}")
	public ResponseEntity<?> listar(@PathVariable(value = "id") long id) {
		Optional<Escala> escala = escalaControle.listar(id);
		return new ResponseEntity<>(EscalaDTO.escalaResposta(escala.get()), HttpStatus.OK);
	}

	@ApiOperation(value = "Retorna lista de Escalas pelo Serviço-Funcionario")
	@GetMapping("/servicofuncionario/{id}")
	public ResponseEntity<?> listarPorServico(@PathVariable(value = "id") long id) {
		return new ResponseEntity<>(EscalaDTO.listarResposta(escalaControle.listarPorservicoFuncionario(id)), HttpStatus.OK);
	}
	
	@ApiOperation(value = "Salva uma Escala")
	@PostMapping("")
	public ResponseEntity<?> salvar(@RequestBody @Valid EscalaDTO escalaDTO) {
		Escala escala = escalaControle.salvar(escalaDTO);
		return new ResponseEntity<>(EscalaDTO.escalaResposta(escala), HttpStatus.CREATED);
	}

	@ApiOperation(value = "Edita uma Escala")
	@PutMapping("")
	public ResponseEntity<?> editar(@RequestBody @Valid EscalaDTO escalaDTO) {
		Escala escala = escalaControle.editar(escalaDTO);
		return new ResponseEntity<>(EscalaDTO.escalaResposta(escala), HttpStatus.ACCEPTED);
	}

	@ApiOperation(value = "Deleta uma Escala por Id")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable(value = "id") long id) {
		escalaControle.deletarById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@ApiOperation(value = "Retorna uma lista completa da Escalas de um servico-funcionario")
	@GetMapping("servicofuncionario")
	public ResponseEntity<?> teste(@RequestParam long funcionario, @RequestParam List<Long> servico) {
		//itemEscalaControle.itensEscalaCompletas(funcionario, servico);
		List<List<EscalaDTO>> lista = new ArrayList<>();
		escalaControle.escalasFuncionarioServico(funcionario, servico).forEach(e->lista.add(EscalaDTO.listarResposta(e)));
		return new ResponseEntity<>(
				lista,
				HttpStatus.OK);
	}

}
