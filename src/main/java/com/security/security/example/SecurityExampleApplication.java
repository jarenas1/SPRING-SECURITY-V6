package com.security.security.example;

import com.security.security.example.entities.PermissionEntity;
import com.security.security.example.entities.RoleEntity;
import com.security.security.example.entities.RoleEnum;
import com.security.security.example.entities.UserEntity;
import com.security.security.example.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Set;

@SpringBootApplication
public class SecurityExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityExampleApplication.class, args);

	}
	@Bean
	CommandLineRunner init(UserRepository userRepository){
		return args ->{
			PermissionEntity permissionEntity = PermissionEntity.builder().name("CREATE").build();
			PermissionEntity permissionEntity1 = PermissionEntity.builder().name("READ").build();
			PermissionEntity permissionEntity2 = PermissionEntity.builder().name("UPDATE").build();
			PermissionEntity permissionEntity3 = PermissionEntity.builder().name("DELETE").build();
			PermissionEntity permissionEntity4 = PermissionEntity.builder().name("REFACTOR").build();
			//ROLES
			RoleEntity roleAdmin = RoleEntity.builder().roleEnum(RoleEnum.ADMIN).permissions(Set.of(permissionEntity, permissionEntity1, permissionEntity2, permissionEntity3)).build();
			RoleEntity roleUser = RoleEntity.builder().roleEnum(RoleEnum.USER).permissions(Set.of(permissionEntity, permissionEntity1)).build();
			RoleEntity roleInvied = RoleEntity.builder().roleEnum(RoleEnum.INVITED).permissions(Set.of(permissionEntity)).build();
			RoleEntity roleDeveloper = RoleEntity.builder().roleEnum(RoleEnum.DEVELOPER).permissions(Set.of(permissionEntity, permissionEntity1, permissionEntity2, permissionEntity3, permissionEntity4)).build();

			//USERS
			UserEntity userAdmin = UserEntity.builder().username("admin").password("1234").roles(Set.of(roleAdmin)).build();

			UserEntity userDeveloper = UserEntity.builder().username("developer").password("1234").roles(Set.of(roleDeveloper)).build();

			userRepository.saveAll(Set.of(userAdmin, userDeveloper));
		};
	}
}
