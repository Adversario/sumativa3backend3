package com.example.accounts.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
  @Bean
  NewTopic accountCreatedTopic() { return TopicBuilder.name("account-created").partitions(1).replicas(1).build(); }
  @Bean
  NewTopic customerUpdatedTopic() { return TopicBuilder.name("customer-updated").partitions(1).replicas(1).build(); }
}
