package com.apirest.TCBackEnd.Notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apirest.TCBackEnd.Config.SecurityConfig;

@RestController
@CrossOrigin(origins = "*")

public class NotificationsController {
	
	@Autowired
	SecurityConfig securityConfig;

	private final NotificationDispatcher dispatcher;
	
	@Autowired
    private SimpMessagingTemplate template;

	@Autowired
	public NotificationsController(NotificationDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	@MessageMapping("/start")
	public void start(StompHeaderAccessor stompHeaderAccessor) {
		dispatcher.add(stompHeaderAccessor.getSessionId());
		System.out.println("stompHeaderAccessor.getSessionId() : " + stompHeaderAccessor.getSessionId());
		//dispatcher.dispatch();
	}

	@MessageMapping("/stop")
	public void stop(StompHeaderAccessor stompHeaderAccessor) {
		dispatcher.remove(stompHeaderAccessor.getSessionId());
	}

	@MessageMapping("/start2")
	public void start2(Authentication auth) {
		System.out.println("auth.getName() = "+auth.getName());
		
		// System.out.println("principal.getName() = "+principal.getName());
		//securityConfig.teste();
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
	
	@GetMapping("/notify")
    public String getNotification() {        

        // Push notifications to front-end
        template.convertAndSend("/notification", "Msg a ser enviada");

        return "Notifications successfully sent to Angular !";
    }

}
