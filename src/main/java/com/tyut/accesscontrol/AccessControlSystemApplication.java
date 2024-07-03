package com.tyut.accesscontrol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class AccessControlSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccessControlSystemApplication.class, args);
	}

}
