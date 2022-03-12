package com.example.mvcdemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/v1/hello/sleep")
    public String getHelloSleep(@RequestParam Long delay){
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            return "InterruptedException";
        }
        return "hola";
    }


}
