package com.example.reactive.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.List;

@RestController
public class GreetingController {

    private final GreetingService greetingService;

    public GreetingController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @GetMapping("/v1/greet")
    public Mono<Void> greet(){
        return greetingService.greet().log();
    }

    @GetMapping("/v1/read")
    public Flux<Greeting> read(){
        return greetingService.read();
    }
}
