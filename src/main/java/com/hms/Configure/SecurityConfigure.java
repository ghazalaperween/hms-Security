package com.hms.Configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;


@Configuration
public class SecurityConfigure {

    private JWTFilter jwtFilter;

    public SecurityConfigure(JWTFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        http.csrf().disable().cors().disable();
        http.addFilterBefore(jwtFilter, AuthorizationFilter.class);
        http.authorizeHttpRequests().anyRequest().permitAll();
        return http.build();

    }
}