package com.mycompany.njtrezervacijearanzmana.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/api/auth/**",
                    "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html"
                ).permitAll()
                .anyRequest().permitAll()   // za sada sve otvoreno; lako promeni na authenticated()
            )
            .httpBasic(Customizer.withDefaults());
        return http.build();
    }
}
