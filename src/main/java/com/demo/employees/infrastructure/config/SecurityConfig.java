package com.demo.employees.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

import static com.demo.employees.infrastructure.adapters.inbound.ApiPaths.ACTUATOR_HEALTH;
import static com.demo.employees.infrastructure.adapters.inbound.ApiPaths.DOCS;
import static com.demo.employees.infrastructure.adapters.inbound.ApiPaths.DOCS_YAML;
import static com.demo.employees.infrastructure.adapters.inbound.ApiPaths.EMPLOYEES;
import static com.demo.employees.infrastructure.adapters.inbound.ApiPaths.SWAGGER_UI;
import static com.demo.employees.infrastructure.adapters.inbound.ApiPaths.SWAGGER_UI_HTML;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  private static final String[] WHITELIST = { DOCS, SWAGGER_UI_HTML, SWAGGER_UI, ACTUATOR_HEALTH ,DOCS_YAML};

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(WHITELIST).permitAll()

                    .requestMatchers(HttpMethod.GET, EMPLOYEES, EMPLOYEES + "/**").hasAuthority("SCOPE_employees.read")
                    .requestMatchers(HttpMethod.GET, EMPLOYEES + "/search").hasAuthority("SCOPE_employees.read")

                    .requestMatchers(HttpMethod.POST,   EMPLOYEES).hasAuthority("SCOPE_employees.write")
                    .requestMatchers(HttpMethod.PUT,    EMPLOYEES + "/**").hasAuthority("SCOPE_employees.write")
                    .requestMatchers(HttpMethod.PATCH,  EMPLOYEES + "/**").hasAuthority("SCOPE_employees.write")
                    .requestMatchers(HttpMethod.DELETE, EMPLOYEES + "/**").hasAuthority("SCOPE_employees.write")

                    .anyRequest().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

    return http.build();
  }

  @Bean
  JwtDecoder jwtDecoder(@Value("${security.jwt.secret}") String secret) {
    SecretKey key = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
    return NimbusJwtDecoder.withSecretKey(key).macAlgorithm(MacAlgorithm.HS256).build();
  }
}
