package com.apirest.TCBackEnd.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceEmail {
	@Autowired
	Mailer mailer;

	public boolean enviarEmail(String destino, String assunto, String corpo) {
		String remetente = "medicamentosolidario@hotmail.com";
		try {
			mailer.enviar(new Mensagem(remetente, destino, assunto, corpo));
			System.out.println("Email enviado: " + destino);
			return true;

		} catch (Exception e) {
			System.out.println(e);

			return false;
		}
	}
}
