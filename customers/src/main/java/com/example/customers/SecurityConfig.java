package com.example.customers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
  @Bean
  SecurityFilterChain filter(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable())
      .authorizeHttpRequests(reg -> reg.requestMatchers("/actuator/**").permitAll().anyRequest().authenticated())
      .oauth2ResourceServer(oauth2 -> oauth2.jwt());
    return http.build();
  }
}
