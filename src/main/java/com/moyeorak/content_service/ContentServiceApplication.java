package com.moyeorak.content_service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class ContentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContentServiceApplication.class, args);
	}

}
