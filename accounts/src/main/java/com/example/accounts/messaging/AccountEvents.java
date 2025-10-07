package com.example.accounts.messaging;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class AccountEvents {

  private final KafkaTemplate<String, Object> kafka;

  public AccountEvents(KafkaTemplate<String, Object> kafka) { this.kafka = kafka; }

  public void publishAccountCreated(String id) {
    kafka.send("account-created", Map.of("accountId", id, "ts", System.currentTimeMillis()));
  }

  @KafkaListener(topics = "customer-updated", groupId = "accounts")
  public void onCustomerUpdated(Map<String,Object> evt) {
    // manejar evento
  }
}
