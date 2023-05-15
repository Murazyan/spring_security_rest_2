package com.example.spring_security_rest_2;

import com.example.spring_security_rest_2.model.Role;
import com.example.spring_security_rest_2.model.User;
import com.example.spring_security_rest_2.model.enums.Gender;
import com.example.spring_security_rest_2.repository.RoleRepository;
import com.example.spring_security_rest_2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class SpringSecurityRest2Application implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityRest2Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if(roleRepository.findByName("ADMIN").isEmpty()){
            roleRepository.saveAll(List.of(new Role("ADMIN"), new Role("USER")));
            userRepository.save(User.builder()
                            .name("ADMIN")
                            .surname("ADMIN")
                            .email("admin@gmail.com")
                            .password(passwordEncoder.encode("123"))
                            .gender(Gender.MALE)
                            .roles(List.of(roleRepository.findByName("ADMIN").get()))
                    .build());
        }
    }
}
