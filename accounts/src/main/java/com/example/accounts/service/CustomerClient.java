package com.example.accounts.service;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Map;

@Service
public class CustomerClient {

  private final WebClient webClient;

  public CustomerClient(WebClient.Builder builder) {
    this.webClient = builder.baseUrl("http://customers:8082").build();
  }

  @CircuitBreaker(name="customers", fallbackMethod="fallback")
  @Retry(name="customers")
  @Bulkhead(name="customers")
  public Map<String,Object> findCustomer(String id) {
    return webClient.get().uri("/api/customers/{id}", id)
      .retrieve().bodyToMono(Map.class).block();
  }

  private Map<String,Object> fallback(String id, Throwable t) {
    return Map.of("id", id, "status", "UNKNOWN", "note", "fallback");
  }
}
