package com.example.demoreactive;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static java.time.temporal.ChronoUnit.MILLIS;

@RestController
public class Controller {

    @GetMapping("/v1/hello/sleep")
    public Mono<String> getHelloSleep(@RequestParam Long delay){
        return Mono.delay(Duration.of(delay, MILLIS))
                .map(result -> "hola");
    }

}
