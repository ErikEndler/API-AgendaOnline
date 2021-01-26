package com.apirest.TCBackEnd.Notification;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.apirest.TCBackEnd.Models.Usuario;

@Controller
@CrossOrigin(origins = "*")

public class NotificationsController {

	private final NotificationDispatcher dispatcher;

	@Autowired
	public NotificationsController(NotificationDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	@MessageMapping("/start")
	public void start(StompHeaderAccessor stompHeaderAccessor) {
		dispatcher.add(stompHeaderAccessor.getSessionId());
		System.out.println("stompHeaderAccessor.getSessionId() : " + stompHeaderAccessor.getSessionId());
		dispatcher.dispatch();
		System.out.println("METODO PEGAR USUARIO : "+pegar()); 

	}

	@MessageMapping("/stop")
	public void stop(StompHeaderAccessor stompHeaderAccessor) {
		dispatcher.remove(stompHeaderAccessor.getSessionId());

	}

	public Usuario pegar() {
		Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null) {
			Object obj = authentication.getPrincipal();

			if (obj instanceof Usuario) {
				return (Usuario) obj;
			}
		}
		return null;

	}

}
