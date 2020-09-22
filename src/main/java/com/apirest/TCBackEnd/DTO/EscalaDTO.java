package com.apirest.TCBackEnd.DTO;

import java.util.ArrayList;
import java.util.List;

import com.apirest.TCBackEnd.Models.Escala;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EscalaDTO {
	
	private long id;
	private String diaSemana;
	private long servico;
	
	public static EscalaDTO escalaResposta(Escala escala) {
		return new EscalaDTO(escala.getId(), escala.getDiaSemana(), escala.getServico().getId());
	}

	// Recebe uma lista de escalas e transforma a lista para o formato de resposta
	public static Iterable<EscalaDTO> listarResposta(Iterable<Escala> listaEscala) {
		// Cria a lista que sera retornada
		List<EscalaDTO> listaDTO = new ArrayList<EscalaDTO>();
		// Faz um for na lista recebida no metodo
		for (Escala escala : listaEscala) {
			listaDTO.add(escalaResposta(escala));
		}
		return listaDTO;
	}

}
