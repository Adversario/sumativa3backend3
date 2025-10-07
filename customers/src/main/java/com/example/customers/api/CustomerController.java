package com.example.customers.api;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

  private final KafkaTemplate<String,Object> kafka;

  public CustomerController(KafkaTemplate<String,Object> kafka) { this.kafka = kafka; }

  @GetMapping("/{id}")
  public Map<String,Object> find(@PathVariable String id) {
    return Map.of("id", id, "name", "Jane Legacy", "segment","VIP");
  }

  @PutMapping("/{id}")
  public Map<String,Object> update(@PathVariable String id, @RequestBody Map<String,Object> body) {
    Map<String,Object> updated = new HashMap<>(body);
    updated.put("id", id);
    kafka.send("customer-updated", updated);
    return updated;
  }
}
