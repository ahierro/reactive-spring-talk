package com.example.reactive.demo;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;


@Repository
public interface GreetingRepository extends ReactiveCrudRepository<Greeting, String> {

    Flux<Greeting> findAllBy(Pageable page);

}
