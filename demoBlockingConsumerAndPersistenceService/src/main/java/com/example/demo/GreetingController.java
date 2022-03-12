package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
public class GreetingController {

    private final GreetingService greetingService;

    public GreetingController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @GetMapping("/v1/greet")
    public void greet(){
        greetingService.greet();
    }

    @GetMapping("/v1/greet/non-blocking")
    public CompletableFuture<Void> greetNonBlocking(){
        return greetingService.greetNonBlocking();
    }
}
