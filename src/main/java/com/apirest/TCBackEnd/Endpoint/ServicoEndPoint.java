package com.apirest.TCBackEnd.Endpoint;

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
import org.springframework.web.bind.annotation.RestController;

import com.apirest.TCBackEnd.Controle.ServicoControle;
import com.apirest.TCBackEnd.Controle.ServicoFuncionarioControle;
import com.apirest.TCBackEnd.DTO.ServicoDTO;
import com.apirest.TCBackEnd.DTO.ServicoFuncionarioDTO;
import com.apirest.TCBackEnd.Models.Servico;
import com.apirest.TCBackEnd.Models.ServicoFuncionario;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api/servico")
@Api(value = "API REST Servico")
@CrossOrigin(origins = "*")
public class ServicoEndPoint {

	@Autowired
	ServicoControle servicoControle;
	@Autowired
	ServicoFuncionarioControle servicoFuncionarioControle;

	@ApiOperation(value = "Retorna uma lista de Serviços")
	@GetMapping("")
	public ResponseEntity<?> listarTodos() {
		return new ResponseEntity<>(ServicoDTO.listarResposta((List<Servico>) servicoControle.listarTodos()),
				HttpStatus.OK);
	}

	@ApiOperation(value = "Retorna um Serviço unico pelo ID")
	@GetMapping("/{id}")
	public ResponseEntity<?> listar(@PathVariable(value = "id") long id) {
		Optional<Servico> servico = servicoControle.listar(id);
		return new ResponseEntity<>(ServicoDTO.servicoResposta(servico.get()), HttpStatus.OK);
	}

	@ApiOperation(value = "Salva um Serviço")
	@PostMapping("")
	public ResponseEntity<?> salvar(@RequestBody @Valid ServicoDTO servicoDTO) {
		Servico categoria = servicoControle.salvar(servicoDTO);
		return new ResponseEntity<>(ServicoDTO.servicoResposta(categoria), HttpStatus.CREATED);
	}

	@ApiOperation(value = "Edita um Serviço")
	@PutMapping("")
	public ResponseEntity<?> editar(@RequestBody @Valid ServicoDTO servicoDTO) {
		Servico servico = servicoControle.editar(servicoDTO);
		return new ResponseEntity<>(ServicoDTO.servicoResposta(servico), HttpStatus.ACCEPTED);
	}

	@ApiOperation(value = "Deleta um Serviço por Id")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable(value = "id") long id) {
		servicoControle.deletarById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	// ----TRATA RELAÇAO FUNCIONARIO-SERVIÇO---------------
	@ApiOperation(value = "Retorna uma lista de Serviços-Funcionario")
	@GetMapping("funcionario")
	public ResponseEntity<?> listarTodos2() {
		return new ResponseEntity<>(ServicoFuncionarioDTO.listarResposta(servicoFuncionarioControle.listarTodos()),
				HttpStatus.OK);
	}

	@ApiOperation(value = "Retorna um Serviço-Funcionario unico pelo ID")
	@GetMapping("funcionario/{id}")
	public ResponseEntity<?> listar2(@PathVariable(value = "id") long id) {
		Optional<ServicoFuncionario> servicoFuncionario = servicoFuncionarioControle.listar(id);
		return new ResponseEntity<>(ServicoFuncionarioDTO.ServicoFuncionarioResposta(servicoFuncionario.get()),
				HttpStatus.OK);
	}

	@ApiOperation(value = "Retorna lista Serviço-Funcionario pelo serviço")
	@GetMapping("funcionario/servico/{id}")
	public ResponseEntity<?> listarPorServico(@PathVariable(value = "id") long id) {
		List<ServicoFuncionario> servicoFuncionario = servicoFuncionarioControle.listarPorServico(id);
		return new ResponseEntity<>(ServicoFuncionarioDTO.listarResposta(servicoFuncionario), HttpStatus.OK);
	}

	@ApiOperation(value = "Retorna lista Serviços de um funcionario")
	@GetMapping("funcionario/funcionario/{id}")
	public ResponseEntity<?> listaServicoFunncionario(@PathVariable(value = "id") long id) {
		return new ResponseEntity<>(
				ServicoFuncionarioDTO.listarResposta(servicoFuncionarioControle.listarServicosDoFuncionario(id)),
				HttpStatus.OK);
	}

	@ApiOperation(value = "Salva um Serviço-Funcionario")
	@PostMapping("funcionario")
	public ResponseEntity<?> salvar(@RequestBody @Valid ServicoFuncionarioDTO servicoFuncionarioDTO) {
		ServicoFuncionario servicoFuncionario = servicoFuncionarioControle.salvar(servicoFuncionarioDTO);
		return new ResponseEntity<>(ServicoFuncionarioDTO.ServicoFuncionarioResposta(servicoFuncionario),
				HttpStatus.CREATED);
	}

	@ApiOperation(value = "Edita um Serviço-Funcionario")
	@PutMapping("funcionario")
	public ResponseEntity<?> editar(@RequestBody @Valid ServicoFuncionarioDTO servicoFuncionarioDTO) {
		ServicoFuncionario servicoFuncionario = servicoFuncionarioControle.editar(servicoFuncionarioDTO);
		return new ResponseEntity<>(ServicoFuncionarioDTO.ServicoFuncionarioResposta(servicoFuncionario),
				HttpStatus.ACCEPTED);
	}

	@ApiOperation(value = "Deleta um Serviço-Funcionario por Id")
	@PostMapping("funcionario/delete")
	public ResponseEntity<?> deleteById2(@RequestBody ServicoFuncionarioDTO dto) {
		servicoFuncionarioControle.deletar(dto.getFuncionario().getId(), dto.getServico().getId());
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
