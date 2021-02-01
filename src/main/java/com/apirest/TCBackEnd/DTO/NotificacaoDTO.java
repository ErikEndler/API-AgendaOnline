package com.apirest.TCBackEnd.DTO;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificacaoDTO {

	private String msg;

	public static NotificacaoDTO notificacaoResposta(String msg) {
		return new NotificacaoDTO(msg);
	}

	// Recebe uma lista de notificacoes e transforma a lista para o formato de resposta
	public static List<NotificacaoDTO> listarResposta(List<String> listaNotificacoes) {
		// Cria a lista que sera retornada
		List<NotificacaoDTO> listaDTO = new ArrayList<NotificacaoDTO>();
		// Faz um for na lista recebida no metodo
		for (String notificacao : listaNotificacoes) {
			listaDTO.add(notificacaoResposta(notificacao));
		}
		return listaDTO;
	}

}
