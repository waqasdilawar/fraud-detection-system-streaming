package com.unifonic.frauddetectionsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
public class FraudDetectionSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(FraudDetectionSystemApplication.class, args);
	}

	//@KafkaListener(id = "myId", topics = "test")
	public void listen(String in) {
		System.out.println(in);
	}
}
