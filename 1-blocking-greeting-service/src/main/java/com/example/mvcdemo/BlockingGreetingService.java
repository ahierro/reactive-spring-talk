package com.example.mvcdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BlockingGreetingService {

	public static void main(String[] args) {
		SpringApplication.run(BlockingGreetingService.class, args);
	}

}
