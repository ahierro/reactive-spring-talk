package com.example.mvcdemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
public class Controller {
    private final SleepService sleepService;

    public Controller(SleepService sleepService) {
        this.sleepService = sleepService;
    }

    @GetMapping("/v1/hello/sleep")
    public String getHelloSleep(@RequestParam Long delay){
        return this.sleepService.getHelloDelayed(delay);
    }
    @GetMapping("/v1/hello/sleep/async")
    public CompletableFuture<String> getHelloSleepAsync(@RequestParam Long delay){
        return this.sleepService.getHelloDelayedAsync(delay);
    }
}
