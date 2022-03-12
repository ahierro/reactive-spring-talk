package com.example.mvcdemo;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class SleepService {
    @Async("async-test")
    public CompletableFuture<String> getHelloDelayedAsync(Long delay) {
        return new AsyncResult<>(this.getHelloDelayed(delay)).completable();
    }

    public String getHelloDelayed(Long delay) {
        if (delay == null || delay == 0L) {
            return getHello();
        }
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            return "InterruptedException";
        }
        return getHello();
    }

    public String getHello() {
        return "hola";
    }
}
