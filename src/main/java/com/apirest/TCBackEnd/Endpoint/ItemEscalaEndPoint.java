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

import com.apirest.TCBackEnd.Controle.ItemescalaControle;
import com.apirest.TCBackEnd.DTO.ItemEscalaDTO;
import com.apirest.TCBackEnd.Models.ItemEscala;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api/itemescala")
@Api(value = "API REST Item Escala")
@CrossOrigin(origins = "*")
public class ItemEscalaEndPoint {
	@Autowired
	ItemescalaControle itemEscalaControle;

	@ApiOperation(value = "Retorna uma lista de Item-Esacala")
	@GetMapping("")
	public ResponseEntity<?> listarTodos() {
		return new ResponseEntity<>(ItemEscalaDTO.listarResposta(itemEscalaControle.listarTodos()), HttpStatus.OK);
	}
	@ApiOperation(value = "Retorna uma lista de Itens-Escala por escala")
	@GetMapping("/escala/{id}")
	public ResponseEntity<?> listarPorEscala(@PathVariable(value = "id") long id) {
		return new ResponseEntity<>(ItemEscalaDTO.listarResposta(itemEscalaControle.listarPorservico(id)), HttpStatus.OK);
	}

	@ApiOperation(value = "Retorna um Item-Escala unico pelo ID")
	@GetMapping("/{id}")
	public ResponseEntity<?> listar(@PathVariable(value = "id") long id) {
		Optional<ItemEscala> itemEscala = itemEscalaControle.listar(id);
		return new ResponseEntity<>(ItemEscalaDTO.ItemEscalaResposta(itemEscala.get()), HttpStatus.OK);
	}

	@ApiOperation(value = "Salva um Item-Escala")
	@PostMapping("")
	public ResponseEntity<?> salvar(@RequestBody @Valid ItemEscalaDTO itemEscalaDTO) {
		ItemEscala itemEscala = itemEscalaControle.salvar(itemEscalaDTO);
		return new ResponseEntity<>(ItemEscalaDTO.ItemEscalaResposta(itemEscala), HttpStatus.CREATED);
	}

	@ApiOperation(value = "Edita um Item-Escala")
	@PutMapping("")
	public ResponseEntity<?> editar(@RequestBody @Valid ItemEscalaDTO itemEscalaDTO) {
		ItemEscala itemEscala = itemEscalaControle.editar(itemEscalaDTO);
		return new ResponseEntity<>(ItemEscalaDTO.ItemEscalaResposta(itemEscala), HttpStatus.ACCEPTED);
	}

	@ApiOperation(value = "Deleta um Item-Escala por Id")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable(value = "id") long id) {
		itemEscalaControle.deletarById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
