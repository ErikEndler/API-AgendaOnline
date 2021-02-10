package com.apirest.TCBackEnd.Notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apirest.TCBackEnd.Config.SecurityConfig;

@RestController
@CrossOrigin(origins = "*")

public class NotificationsController {

	@Autowired
	SecurityConfig securityConfig;

	private static final Logger LOGGER = LoggerFactory.getLogger(NotificationDispatcher.class);

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
		// dispatcher.dispatch();
	}

	@MessageMapping("/stop")
	public void stop(StompHeaderAccessor stompHeaderAccessor) {
		dispatcher.remove(stompHeaderAccessor.getSessionId());
	}

	@GetMapping("/subscribe/notification")
	public ResponseEntity<?> getNotification() {
		LOGGER.info(SecurityContextHolder.getContext().getAuthentication().getName());
		dispatcher.add2(SecurityContextHolder.getContext().getAuthentication().getName());

		return new ResponseEntity<>(HttpStatus.OK);
	}

}
