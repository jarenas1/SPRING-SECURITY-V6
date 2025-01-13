package com.security.security.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@EnableMethodSecurity
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

    @Bean
    public AuthenticationManager authenticationManaer(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean //Creamos provedor de auntenticacion junto con sus 2 componentes
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(null); //hashea la contraseña y la compara con la de la database
        authenticationProvider.setUserDetailsService(null); //tRAE EL USUARIO DE LA DATABASE
        return authenticationProvider;
    }
}
