package com.security.security.example.services;

import com.security.security.example.entities.UserEntity;
import com.security.security.example.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        //OBTENDREMOS LOS PERMISOS, ROLES Y LOS PASAREMOS A TIPO SimpleGrandtedAuthority en una LISTA
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        //CREAMOS UN ROLE_NAME por cada rol que tenga el usuario, y lo añadiremos a la lista delcarada arriba, se añade ROLE_
        //ya que esta es la forma en la que security reconoce los roles, asi que si no se llaman asi en la db debemos ponerlo
        userEntity.getRoles().forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));

        //AHORA CREAREMOS UNA LISTA DE LOS PERMISOS, RECORDAR QUE ESTAN ASOCIADOS A LOS ROLES!!!!
        userEntity.getRoles().stream()
                .flatMap(role -> role.getPermissions().stream()) //Hacemos un recorrido por los roles, flarmap permite esto
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));
        //RETORNAMOS UN User de Security
        return new User(userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.isEnabled(),
                userEntity.isAccountNonExpired(),
                userEntity.isCredentialsNonExpired(),
                userEntity.isAccountNonLocked(),
                authorityList);
    }
}
