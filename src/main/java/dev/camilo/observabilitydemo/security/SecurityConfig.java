package dev.camilo.observabilitydemo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  // login with simple form
  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
        .authorizeHttpRequests( auth -> {
          auth.requestMatchers("/").permitAll();
          auth.requestMatchers("/actuator/**").permitAll();
          auth.anyRequest().authenticated();
        })
        .formLogin(Customizer.withDefaults())
        .build();
  }

  // user in memory
  @Bean
  public UserDetailsService userDetailsService(){
    return new InMemoryUserDetailsManager(
        User.withUsername("camilo")
            .password("{noop}password")
            .roles("USER")
            .build()
        );
  }

}
