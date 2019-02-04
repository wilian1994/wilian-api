package com.example.wilian.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@SpringBootApplication
public class WilianApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(WilianApiApplication.class, args);
	}
}
