package dev.vandael.jauth.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import dev.vandael.jauth.JWT.JWTAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  @Autowired
  private JWTAuthenticationFilter authenticationFilter;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http.httpBasic(httpBasic -> httpBasic.disable()).csrf(csrf -> csrf.disable())
        .formLogin(formLogin -> formLogin.disable())
        .addFilterAt(authenticationFilter, UsernamePasswordAuthenticationFilter.class).build();
  }
}
