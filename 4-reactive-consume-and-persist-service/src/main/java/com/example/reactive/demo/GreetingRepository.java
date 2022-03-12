package com.example.reactive.demo;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GreetingRepository extends ReactiveCrudRepository<Greeting, String> {
}
