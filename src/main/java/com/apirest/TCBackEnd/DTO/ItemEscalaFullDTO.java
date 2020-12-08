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
public class ItemEscalaFullDTO {
	
	private long id;	
	private EscalaDTO escala;
	private String hrInicial;
	private String hrFinal;
	
	public static ItemEscalaFullDTO ItemEscalaResposta(ItemEscala itemEscala) {
		return new ItemEscalaFullDTO(itemEscala.getId(), EscalaDTO.escalaResposta(itemEscala.getEscala()),
				itemEscala.getHrInicial().toString(), itemEscala.getHrFinal().toString());
	}

	// Recebe uma lista de ItemEscala e transforma a lista para o formato de
	// resposta
	public static Iterable<ItemEscalaFullDTO> listarResposta(Iterable<ItemEscala> listaItemEscala) {
		// Cria a lista que sera retornada
		List<ItemEscalaFullDTO> listaDTO = new ArrayList<ItemEscalaFullDTO>();
		// Faz um for na lista recebida no metodo
		for (ItemEscala escala : listaItemEscala) {
			listaDTO.add(ItemEscalaResposta(escala));
		}
		return listaDTO;
	}

}
