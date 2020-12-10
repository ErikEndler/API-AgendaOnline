package com.apirest.TCBackEnd.DTO;

import java.util.ArrayList;
import java.util.List;

import com.apirest.TCBackEnd.Models.ServicoFuncionario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ServicoFuncionarioReduzidoDTO {
	private long id;
	private long funcionarioId;
	private long servicoId;

	public static ServicoFuncionarioReduzidoDTO ServicoFuncionarioResposta(ServicoFuncionario servicoFuncionario) {
		return new ServicoFuncionarioReduzidoDTO(servicoFuncionario.getId(),
				servicoFuncionario.getFuncionario().getId(), servicoFuncionario.getServico().getId());
	}

	// Recebe uma lista de servicos e transforma a lista para o formato de resposta
	public static Iterable<ServicoFuncionarioReduzidoDTO> listarResposta(
			Iterable<ServicoFuncionario> listaServicoFuncionario) {
		// Cria a lista que sera retornada
		List<ServicoFuncionarioReduzidoDTO> listaDTO = new ArrayList<ServicoFuncionarioReduzidoDTO>();
		// Faz um for na lista recebida no metodo
		for (ServicoFuncionario servicoFuncionario : listaServicoFuncionario) {
			listaDTO.add(ServicoFuncionarioResposta(servicoFuncionario));
		}
		return listaDTO;
	}

}
