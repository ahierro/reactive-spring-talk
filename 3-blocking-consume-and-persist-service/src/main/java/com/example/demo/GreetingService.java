package com.example.demo;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

@Service
public class GreetingService {

    private final GreetingRepository repository;
    private final RestTemplate restTemplate;
    Executor executor = Executors.newSingleThreadScheduledExecutor();

    public GreetingService(GreetingRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    private String getHelloFromService(Long delay) {
        return restTemplate.getForEntity("http://localhost:8080/v1/hello/sleep?delay={delay}", String.class, delay).getBody();
    }

    public void greet() {
        Greeting greeting = new Greeting();
        greeting.setId(UUID.randomUUID().toString());
        greeting.setGreeting(this.getHelloFromService(1000L));
        this.repository.save(greeting);
    }
    @Async("async-test")
    public CompletableFuture<Void> greetNonBlocking() {
        greet();
        return new AsyncResult<Void>(null).completable();

//        return (CompletableFuture.allOf(
//                CompletableFuture.supplyAsync(() ->getHelloFromService(1000L), executor)
//                .thenCompose(greetingResultFromWebService -> {
//                    Greeting greeting = new Greeting();
//                    greeting.setId(UUID.randomUUID().toString());
//                    greeting.setGreeting(greetingResultFromWebService);
//                    return CompletableFuture.completedFuture(greeting);
//                })
//                .thenCompose(greeting -> CompletableFuture.completedFuture(this.repository.save(greeting)))));
    }
}
