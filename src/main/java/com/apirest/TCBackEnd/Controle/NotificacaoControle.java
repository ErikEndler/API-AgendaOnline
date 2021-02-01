package com.apirest.TCBackEnd.Controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apirest.TCBackEnd.Models.NotificacaoUsuario;
import com.apirest.TCBackEnd.Models.Usuario;
import com.apirest.TCBackEnd.Notification.NotificationDispatcher;
import com.apirest.TCBackEnd.Repository.NotificacaoUsuarioRepository;

@Service
public class NotificacaoControle {

	@Autowired
	NotificacaoUsuarioRepository repository;
	@Autowired
	NotificationDispatcher notificationDispatcher;

	// Emitir uma nova Notificação CUSTOM que é enviada no socket do usuario
	public void notificacaoCustom(String cpf, String msg) {
		notificationDispatcher.enviarMSG(cpf, msg);
	}

	// cria a entidade notificação do usuario
	public void notificacaoCriacao(Usuario usuario) {
		NotificacaoUsuario notificacao = new NotificacaoUsuario();
		notificacao.setUsuario(usuario);
		repository.save(notificacao);
	}

	public void NotificacaoCancelado(long idUsuario, String acao) {
		NotificacaoUsuario notificacaoUsuario = repository.findByUsuarioId(idUsuario).get();
		int i = notificacaoUsuario.getQtdCancelado();
		if (acao.equals("++")) {
			notificacaoUsuario.setQtdCancelado(i++);
		} else if (acao.equals("--")) {
			notificacaoUsuario.setQtdCancelado(i--);
		}
		// chamada para emitir para o canal
	}

	public void NotificacaoNovo(long idUsuario, String acao) {
		NotificacaoUsuario notificacaoUsuario = repository.findByUsuarioId(idUsuario).get();
		int i = notificacaoUsuario.getQtdNovos();
		if (acao.equals("++")) {
			notificacaoUsuario.setQtdNovos(i++);
		} else if (acao.equals("--")) {
			notificacaoUsuario.setQtdNovos(i--);
		}
		// chamada para emitir para o canal
	}

	public void NotificacaoPendente(long idUsuario, String acao) {
		NotificacaoUsuario notificacaoUsuario = repository.findByUsuarioId(idUsuario).get();
		int i = notificacaoUsuario.getQtdPendentes();
		if (acao.equals("++")) {
			notificacaoUsuario.setQtdPendentes(i++);
		} else if (acao.equals("--")) {
			notificacaoUsuario.setQtdPendentes(i--);
		}
		// chamada para emitir para o canal
	}

}
