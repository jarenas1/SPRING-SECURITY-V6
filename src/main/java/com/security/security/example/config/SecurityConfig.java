package com.security.security.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@EnableMethodSecurity //PERMITE CONFIGURAR CON ANOTACIONES
public class SecurityConfig {
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//         return httpSecurity
//                 .csrf(csrf -> csrf.disable()) //Evitar captacion de datos de formularios solo thymeleaf, si no se trabajara con formularios, desactivar
//                 .httpBasic(Customizer.withDefaults()) //PERMITE AUTENTICACION BASICA, ES DECIR USUARIO Y CONTRASEÑA
//                 //STATELESS, quiere decir que no se trabajara con sesion, es decir, no se creara una sesion en la base de datos como en laravel, si no que se trabajara con tokwn
//                 .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                 //PERMISOS A ENDPOINTS: METODO Y RUTA
//                 .authorizeHttpRequests(httpRequests -> {
//                     httpRequests.requestMatchers(HttpMethod.GET, "/auth/index2").permitAll(); //publico
//                     httpRequests.requestMatchers(HttpMethod.GET, "/auth/index").hasAuthority("READ"); //debe tener permiso de leer PRIVADO
//
//                     httpRequests.anyRequest().denyAll(); //cualquier otro trquest, que no este definido aca, deniega el acceso
//
////                     httpRequests.anyRequest().authenticated(); Los endpoints no definidos arriba, va a permitir acceso si y solo si el usuario esta autenticado
//                         })
//                 .build();
//    }

//FILTRO DE SEGURIDAD POR MEDIO DE ANOTACIONES, RECORDAR QUE LOS CONTROLADORES DEBEN TENER @PreAuthorize("denyAll()") y cada endpoint su anotacion

@Bean
public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
            .csrf(csrf -> csrf.disable()) //Evitar captacion de datos de formularios solo thymeleaf, si no se trabajara con formularios, desactivar
            .httpBasic(Customizer.withDefaults()) //PERMITE AUTENTICACION BASICA, ES DECIR USUARIO Y CONTRASEÑA
            //STATELESS, quiere decir que no se trabajara con sesion, es decir, no se creara una sesion en la base de datos como en laravel, si no que se trabajara con tokwn
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .build();
}

    @Bean
    public AuthenticationManager authenticationManaer(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean //Creamos provedor de auntenticacion junto con sus 2 componentes
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder()); //hashea la contraseña y la compara con la de la database
        authenticationProvider.setUserDetailsService(userDetailsService()); //tRAE EL USUARIO DE LA DATABASE
        return authenticationProvider;
    }

    //DEBE ESTAR CONECTADO A UNA DB, Al devolver el usuario de la data base debe castearse a tipo UserDetails
    @Bean
    public UserDetailsService userDetailsService() {
        //PODEMOS TAMBIEN RETORNAR VARIOS UUSARIOS CON List<UserDetails>
        UserDetails userDetails = User.withUsername("juan") //User es el user de spring security
                .password("1234")
                .roles("ADMIN")
                .authorities("READ","CREATE")
                .build();
        return new InMemoryUserDetailsManager(userDetails);
    }

    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance(); //SOLO EJEMPLO, NO HASHEA CONTRASEÑAS!!!
    }

}
