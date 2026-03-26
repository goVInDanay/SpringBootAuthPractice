package com.example.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EntityScan("com.example.entityservice.models")
public class UberBackendAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(UberBackendAuthApplication.class, args);
	}

}
