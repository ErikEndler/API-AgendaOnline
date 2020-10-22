package com.apirest.TCBackEnd.DTO;

import java.util.ArrayList;
import java.util.List;

import com.apirest.TCBackEnd.Models.Agendamento;
import com.apirest.TCBackEnd.Models.Atendimento;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AtendimentoDTO {
	private long id;

	private long agendamento;

	private long funcionario;
	private String inicio;
	private String fim;

	public static AtendimentoDTO agendamentoResposta(Atendimento atendimento) {
		return new AtendimentoDTO(atendimento.getId(), atendimento.getAgendamento().getId(),
				atendimento.getFuncionario().getId(), atendimento.getInicio().toString(),
				atendimento.getFim().toString());
	}

	// Recebe uma lista de Agendamentos e transforma a lista para o formato de
	// resposta
	public static Iterable<AtendimentoDTO> listarResposta(Iterable<Atendimento> listaAtendimento) {
		// Cria a lista que sera retornada
		List<AtendimentoDTO> listaDTO = new ArrayList<AtendimentoDTO>();
		// Faz um for na lista recebida no metodo
		for (Atendimento categoria : listaAtendimento) {
			listaDTO.add(agendamentoResposta(categoria));
		}
		return listaDTO;
	}

}
