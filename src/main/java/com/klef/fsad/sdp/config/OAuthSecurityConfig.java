package com.klef.fsad.sdp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class OAuthSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http.csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/oauth/**",
                                "/login/**"
                        ).permitAll()
                        .anyRequest().permitAll()
                )

                .oauth2Login(oauth -> oauth
                        .defaultSuccessUrl(
                                "/oauth/verify",
                                true
                        )
                );

        return http.build();
    }
}