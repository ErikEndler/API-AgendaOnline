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

import com.apirest.TCBackEnd.Controle.CategoriaControle;
import com.apirest.TCBackEnd.DTO.CategoriaDTO;
import com.apirest.TCBackEnd.Models.Categoria;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api/categoria")
@Api(value = "API REST Categoria")
@CrossOrigin(origins = "*")
public class CategoriaEndPoint {
	@Autowired
	CategoriaControle categoriaControle;

	@ApiOperation(value = "Retorna uma lista de Categorias")
	@GetMapping("")
	public ResponseEntity<?> listarTodos() {
		return new ResponseEntity<>(CategoriaDTO.listarResposta(categoriaControle.listarTodos()), HttpStatus.OK);
	}

	@ApiOperation(value = "Retorna uma Categoria unico pelo ID")
	@GetMapping("/{id}")
	public ResponseEntity<?> listar(@PathVariable(value = "id") long id) {
		Optional<Categoria> categoria = categoriaControle.listar(id);
		return new ResponseEntity<>(CategoriaDTO.categoriaResposta(categoria.get()), HttpStatus.OK);
	}

	@ApiOperation(value = "Salva uma Categoria")
	@PostMapping("")
	public ResponseEntity<?> salvar(@RequestBody @Valid CategoriaDTO categoriaDTO) {
		Categoria categoria = categoriaControle.salvar(categoriaDTO);
		return new ResponseEntity<>(CategoriaDTO.categoriaResposta(categoria), HttpStatus.CREATED);
	}

	@ApiOperation(value = "Edita uma Categoria")
	@PutMapping("")
	public ResponseEntity<?> editar(@RequestBody @Valid CategoriaDTO categoriaDTO) {
		Categoria categoria = categoriaControle.editar(categoriaDTO);
		return new ResponseEntity<>(CategoriaDTO.categoriaResposta(categoria), HttpStatus.ACCEPTED);
	}

	@ApiOperation(value = "Deleta uma Categoria por Id")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable(value = "id") long id) {
		categoriaControle.deletarById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
