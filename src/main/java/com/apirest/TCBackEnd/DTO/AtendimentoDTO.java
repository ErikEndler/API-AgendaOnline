package com.apirest.TCBackEnd.DTO;

import java.util.ArrayList;
import java.util.List;

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

	private AgendamentoDTO agendamento;

	private UsuarioDTO funcionario;
	private String inicio;
	private String fim;

	public static AtendimentoDTO atendimentoResposta(Atendimento atendimento) {
		return new AtendimentoDTO(atendimento.getId(), AgendamentoDTO.agendamentoResposta(atendimento.getAgendamento()),
				UsuarioDTO.usuarioResposta(atendimento.getFuncionario()), String.valueOf(atendimento.getInicio()),
				String.valueOf(atendimento.getFim()));
	}

	// Recebe uma lista de Agendamentos e transforma a lista para o formato de
	// resposta
	public static Iterable<AtendimentoDTO> listarResposta(Iterable<Atendimento> listaAtendimento) {
		// Cria a lista que sera retornada
		List<AtendimentoDTO> listaDTO = new ArrayList<AtendimentoDTO>();
		// Faz um for na lista recebida no metodo
		for (Atendimento categoria : listaAtendimento) {
			listaDTO.add(atendimentoResposta(categoria));
		}
		return listaDTO;
	}

}
