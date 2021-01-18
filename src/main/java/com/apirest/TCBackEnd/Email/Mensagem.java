package com.apirest.TCBackEnd.Email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class Mensagem {
	private String remetente;

	private String destinatario;

	private String assunto;

	private String corpo;

}
