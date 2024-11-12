package com.realtimechat.realtimechatapp.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(https->https.requestMatchers("/api/**").authenticated()
                .anyRequest().permitAll()
        );

       http.sessionManagement(e->e.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
       http.addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class);

       http.csrf(e->e.disable());
       http.cors(e->e.configurationSource(new CorsConfigurationSource() {
           @Override
           public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
               CorsConfiguration config = new CorsConfiguration();
               config.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
               config.setAllowedMethods(Collections.singletonList("*"));
               config.setAllowCredentials(true);
               config.setAllowedHeaders(Collections.singletonList("*"));
               config.setExposedHeaders(Collections.singletonList("Authorization"));
               return config;
           }
       }));




        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
