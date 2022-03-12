package com.example.mvcdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@SpringBootApplication
@EnableAsync
public class MvcdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MvcdemoApplication.class, args);
	}

	@Bean(name = "async-test")
	public Executor threadPoolTaskExecutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(200);
		taskExecutor.setMaxPoolSize(200);
		taskExecutor.setQueueCapacity(200);
		return taskExecutor;
	}
}
