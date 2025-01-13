package com.security.security.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
         return httpSecurity
                 //.csrf(csrf -> csrf.disable()) //Evitar captacion de datos de formularios, si no se trabajara con formularios, desactivar
                 .authorizeHttpRequests(authorize -> authorize //manejar loe endpoints para indicar cuales son publicos y cuales no
                     .requestMatchers("/v1/index2").permitAll() //rutas libres
                     .anyRequest().authenticated()) //rutas que deben estar autenticadas SIN ROL ESPECIFICO

                 .build();
    }
}
