package com.apirest.TCBackEnd.DTO;

import java.util.ArrayList;
import java.util.List;

import com.apirest.TCBackEnd.Models.Avaliacao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AvaliacaoDTO {
	private long id;
	private AtendimentoDTO atendimento;
	private int notaCliente;
	private int notaFuncionario;
	private String obsCliente;
	private String obsuncionario;

	public static AvaliacaoDTO avaliacaoResposta(Avaliacao avaliacao) {
		return new AvaliacaoDTO(avaliacao.getId(), AtendimentoDTO.atendimentoResposta(avaliacao.getAtendimento()),
				avaliacao.getNotaCliente(), avaliacao.getNotaFuncionario(), avaliacao.getObsCliente(),
				avaliacao.getObsuncionario());
	}

	// Recebe uma lista de Agendamentos e transforma a lista para o formato de
	// resposta
	public static Iterable<AvaliacaoDTO> listarResposta(Iterable<Avaliacao> listaAvaliacao) {
		// Cria a lista que sera retornada
		List<AvaliacaoDTO> listaDTO = new ArrayList<AvaliacaoDTO>();
		// Faz um for na lista recebida no metodo
		for (Avaliacao avaliacao : listaAvaliacao) {
			listaDTO.add(avaliacaoResposta(avaliacao));
		}
		return listaDTO;
	}

}
