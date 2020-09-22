package com.apirest.TCBackEnd.DTO;

import java.util.ArrayList;
import java.util.List;

import com.apirest.TCBackEnd.Models.Servico;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServicoDTO {

	private long id;
	private long categoria;
	private String nome;
	private String descricao;

	public static ServicoDTO servicoResposta(Servico servico) {
		return new ServicoDTO(servico.getId(), servico.getCategoria().getId(), servico.getNome(),
				servico.getDescricao());
	}

	// Recebe uma lista de servicos e transforma a lista para o formato de resposta
	public static Iterable<ServicoDTO> listarResposta(Iterable<Servico> listaServicos) {
		// Cria a lista que sera retornada
		List<ServicoDTO> listaDTO = new ArrayList<ServicoDTO>();
		// Faz um for na lista recebida no metodo
		for (Servico servico : listaServicos) {
			listaDTO.add(servicoResposta(servico));
		}
		return listaDTO;
	}

}
