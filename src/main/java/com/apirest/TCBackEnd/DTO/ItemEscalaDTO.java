package com.apirest.TCBackEnd.DTO;

import java.util.ArrayList;
import java.util.List;

import com.apirest.TCBackEnd.Models.ItemEscala;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemEscalaDTO {

	private long id;
	private long escala;
	private String hrInicial;
	private String hrFinal;

	public static ItemEscalaDTO ItemEscalaResposta(ItemEscala itemEscala) {
		return new ItemEscalaDTO(itemEscala.getId(), itemEscala.getEscala().getId(),
				itemEscala.getHrInicial().toString(), itemEscala.getHrFinal().toString());
	}

	// Recebe uma lista de ItemEscala e transforma a lista para o formato de
	// resposta
	public static List<ItemEscalaDTO> listarResposta(Iterable<ItemEscala> listaItemEscala) {
		// Cria a lista que sera retornada
		List<ItemEscalaDTO> listaDTO = new ArrayList<ItemEscalaDTO>();
		// Faz um for na lista recebida no metodo
		for (ItemEscala escala : listaItemEscala) {
			listaDTO.add(ItemEscalaResposta(escala));
		}
		return listaDTO;
	}

}
