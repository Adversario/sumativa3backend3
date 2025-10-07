package com.example.accounts.api;

import com.example.accounts.service.CustomerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

  private final CustomerClient customerClient;

  public AccountController(CustomerClient customerClient) {
    this.customerClient = customerClient;
  }

  @GetMapping("/{id}")
  public Map<String, Object> getAccount(@PathVariable String id) {
    Map<String, Object> acc = new HashMap<>();
    acc.put("id", id);
    acc.put("type", "CHECKING");
    acc.put("balance", 1200);
    Map<String,Object> cust = customerClient.findCustomer("c-100");
    acc.put("customer", cust);
    return acc;
  }

  @PostMapping
  public ResponseEntity<?> create() {
    return ResponseEntity.accepted().body(Map.of("status","ACCOUNT_CREATED_EVENT_SENT"));
  }
}
