package com.apirest.TCBackEnd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TcBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(TcBackEndApplication.class, args);

	}

}
