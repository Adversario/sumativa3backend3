package com.example.authserver.config;

import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.server.authorization.*;
import org.springframework.security.oauth2.server.authorization.client.*;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.SecurityFilterChain;
import java.util.UUID;

@Configuration
public class SecurityConfig {

  @Bean
  SecurityFilterChain defaultSecurity(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
        .formLogin().and().csrf(csrf -> csrf.ignoringRequestMatchers("/oauth2/token"));
    return http.build();
  }

  @Bean
  public RegisteredClientRepository registeredClientRepository() {
    RegisteredClient client = RegisteredClient.withId(UUID.randomUUID().toString())
      .clientId("gateway-client")
      .clientSecret(passwordEncoder().encode("gateway-secret"))
      .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
      .scope("accounts.read").scope("customers.read").scope("accounts.write")
      .build();
    return new InMemoryRegisteredClientRepository(client);
  }

  @Bean
  public OAuth2AuthorizationService authorizationService() {
    return new InMemoryOAuth2AuthorizationService();
  }

  @Bean
  public UserDetailsService userDetailsService() {
    PasswordEncoder pe = passwordEncoder();
    UserDetails user = User.withUsername("demo").password(pe.encode("demo123")).roles("USER").build();
    return new InMemoryUserDetailsManager(user);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Bean
  public AuthorizationServerSettings providerSettings() {
    return AuthorizationServerSettings.builder().issuer("http://auth-server:9000").build();
  }
}
