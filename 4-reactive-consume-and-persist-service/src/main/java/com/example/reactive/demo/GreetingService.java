package com.example.reactive.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.UUID;
import java.util.List;

@Service
public class GreetingService {
    Logger logger = LoggerFactory.getLogger(GreetingService.class);

    private final GreetingRepository repository;
    private WebClient client;

    public GreetingService(GreetingRepository repository) {
        this.repository = repository;
    }
    @PostConstruct
    public void init(){
        client = WebClient.builder()
                .baseUrl("http://localhost:8080")
                .defaultCookie("cookieKey", "cookieValue")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    private Mono<String> getHelloFromService(Long delay) {
        return WebClient.builder()
                .baseUrl("http://localhost:8080")
                .defaultCookie("cookieKey", "cookieValue")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build().get()
                .uri("/v1/hello/sleep?delay="+ delay)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<Void> greet() {
        Greeting greeting = new Greeting();
        greeting.setId(UUID.randomUUID());
        return this.getHelloFromService(1000L).flatMap(greet -> {
            greeting.setGreeting(greet);
            return this.repository.save(greeting);
        }) .flatMap(x -> Mono.empty());
    }

    public Flux<Greeting> read() {
//               .timeout(Duration.ofMillis(100000))
//                       .take(Duration.ofMillis(100))
       return this.repository.findAllBy(PageRequest.of(0,10))
               .log()
               .take(5,true)
               ;
    }
}
