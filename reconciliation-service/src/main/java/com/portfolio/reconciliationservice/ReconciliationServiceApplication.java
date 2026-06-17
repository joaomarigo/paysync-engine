package com.portfolio.reconciliationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling; // Importante!

@SpringBootApplication
@EnableScheduling // Liga o motor de tarefas em segundo plano
public class ReconciliationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReconciliationServiceApplication.class, args);
	}

}