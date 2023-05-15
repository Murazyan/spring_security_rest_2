package com.example.spring_security_rest_2.service.impl;

import com.example.spring_security_rest_2.dto.request.AuthenticationRequest;
import com.example.spring_security_rest_2.dto.response.AuthenticationResponse;
import com.example.spring_security_rest_2.model.User;
import com.example.spring_security_rest_2.repository.UserRepository;
import com.example.spring_security_rest_2.service.AuthService;
import com.example.spring_security_rest_2.service.UserService;
import com.example.spring_security_rest_2.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {


    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;

    public AuthenticationResponse login(AuthenticationRequest authRequest) {
        Optional<User> userOpt = userRepository.findByEmail(authRequest.getEmail());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
                return AuthenticationResponse.builder()
                        .success(true)
                        .token(jwtTokenUtil.generateToken(user))
                        .message("Ok")
                        .build();
            }
        }
        return AuthenticationResponse.builder()
                .success(false)
                .message("Fail")
                .build();

    }
}
