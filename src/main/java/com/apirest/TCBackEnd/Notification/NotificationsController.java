package com.apirest.TCBackEnd.Notification;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.apirest.TCBackEnd.Config.SecurityConfig;
import com.apirest.TCBackEnd.Models.Usuario;

@Controller
@CrossOrigin(origins = "*")

public class NotificationsController {
	@Autowired
	SecurityConfig securityConfig;

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
	}

	@MessageMapping("/stop")
	public void stop(StompHeaderAccessor stompHeaderAccessor) {
		dispatcher.remove(stompHeaderAccessor.getSessionId());
	}

	@MessageMapping("/start2")
	public void start2() {
		// Object principal =
		// SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		// System.out.println("principal.getName() = "+principal.getName());
		securityConfig.teste();
		// dispatcher.add2(cpf);

		// System.out.println("SecurityContextHolder.getContext().getAuthentication().getName()
		// : "
		// + SecurityContextHolder.getContext().getAuthentication().getName());
		// dispatcher.enviarMSG(cpf, "seu cpf é : " + cpf);
		;
	}

	@MessageMapping("/stop2")
	public void stop2() {
		dispatcher.remove2(SecurityContextHolder.getContext().getAuthentication().getName());
	}
	
	

}
