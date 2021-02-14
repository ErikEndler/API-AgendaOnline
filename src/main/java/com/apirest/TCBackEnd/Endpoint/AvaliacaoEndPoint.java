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

import com.apirest.TCBackEnd.Controle.AvaliacaoControle;
import com.apirest.TCBackEnd.DTO.AvaliacaoDTO;
import com.apirest.TCBackEnd.Models.Avaliacao;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api/avaliacao")
@Api(value = "API REST Avaliação")
@CrossOrigin(origins = "*")
public class AvaliacaoEndPoint {

	@Autowired
	AvaliacaoControle avaliacaoControle;

	@ApiOperation(value = "Retorna uma lista de avaliação")
	@GetMapping("")
	public ResponseEntity<?> listarTodos() {
		return new ResponseEntity<>(AvaliacaoDTO.listarResposta(avaliacaoControle.listarTodos()), HttpStatus.OK);
	}

	@ApiOperation(value = "Retorna uma Avaliação unica pelo ID")
	@GetMapping("/{id}")
	public ResponseEntity<?> listar(@PathVariable(value = "id") long id) {
		Optional<Avaliacao> avaliacao = avaliacaoControle.listar(id);
		return new ResponseEntity<>(AvaliacaoDTO.avaliacaoResposta(avaliacao.get()), HttpStatus.OK);
	}

	@ApiOperation(value = "Retorna uma Avaliação unica pelo ID do atendimento")
	@GetMapping("/atendimento/{id}")
	public ResponseEntity<AvaliacaoDTO> listarPorAtendimento(@PathVariable(value = "id") long id) {
		if (avaliacaoControle.findByAtendimento(id).isPresent()) {
			Avaliacao avaliacao = avaliacaoControle.findByAtendimento(id).get();
			return new ResponseEntity<>(AvaliacaoDTO.avaliacaoResposta(avaliacao), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ApiOperation(value = "Salva uma Avaliação")
	@PostMapping("")
	public ResponseEntity<?> salvar(@RequestBody @Valid AvaliacaoDTO avaliacaoDTO) {
		Avaliacao avaliacao = avaliacaoControle.salvar(avaliacaoDTO);
		return new ResponseEntity<>(AvaliacaoDTO.avaliacaoResposta(avaliacao), HttpStatus.CREATED);
	}

	@ApiOperation(value = "Edita uma Avaliação")
	@PutMapping("")
	public ResponseEntity<?> editar(@RequestBody @Valid AvaliacaoDTO avaliacaoDTO) {
		Avaliacao avaliacao = avaliacaoControle.editar(avaliacaoDTO);
		return new ResponseEntity<>(AvaliacaoDTO.avaliacaoResposta(avaliacao), HttpStatus.ACCEPTED);
	}

	@ApiOperation(value = "Deleta uma Avaliação por Id")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable(value = "id") long id) {
		avaliacaoControle.deletarById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ApiOperation(value = "Retorna Lista de avaliaçoes pelo ID do usuario")
	@GetMapping("/usuario/{id}")
	public ResponseEntity<?> minhasAvaliacoes(@PathVariable(value = "id") long id) {
		return new ResponseEntity<>(AvaliacaoDTO.listarResposta(avaliacaoControle.minhasAvaliacoes(id)), HttpStatus.OK);
	}

}
