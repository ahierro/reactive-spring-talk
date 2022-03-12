package com.example.demoreactive;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.blockhound.BlockHound;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static java.time.temporal.ChronoUnit.MILLIS;

@RestController
public class Controller {
    static {
        BlockHound.install();
    }
    private WebClient client;

    @PostConstruct
    public void init(){
        client = WebClient.builder()
                .baseUrl("http://localhost:8081")
                .defaultCookie("cookieKey", "cookieValue")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @GetMapping("/v1/hello/sleep")
    public Mono<Object> getHelloSleep(@RequestParam Long delay){
        return Mono.delay(Duration.of(delay, MILLIS))
                .map(result -> "hola");
    }
//    @GetMapping("/v1/hello/block")
//    public Mono<String> getHelloBlock(@RequestParam Long delay){
//        return Mono.fromCallable(() -> helloBlock(delay)).subscribeOn(Schedulers.boundedElastic());
//    }
    @GetMapping("/v1/hello/block2")
    public Mono<String> getHelloBlock2(@RequestParam Long delay){
        return Mono.fromFuture(() -> getStringCompletableFuture(delay));
    }
    @GetMapping("/v1/hello/block3")
    public Mono<String> getHelloBlock3(@RequestParam Long delay){
        return getHelloFromService(delay);
    }

    private Mono<String> getHelloFromService(Long delay) {
       return client.get()
                .uri("/v1/hello/sleep?delay="+ delay)
                .retrieve()
                .bodyToMono(String.class);
    }

    public String helloBlock(@RequestParam Long delay){
        try {
            return getStringCompletableFuture(delay).get();
        } catch (InterruptedException e) {
            return "InterruptedException";
        } catch (ExecutionException e) {
            return "ExecutionException";
        }
    }

    private CompletableFuture<String> getStringCompletableFuture(Long delay) {
        return CompletableFuture.supplyAsync(()-> getHelloDelayed(delay));
    }

    public String getHelloDelayed(Long delay) {
        if(delay==null || delay == 0L){
            return getHello() ;
        }
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            return "InterruptedException";
        }
        return getHello() ;
    }
    public String getHello() {
        return "hola";
    }
}
