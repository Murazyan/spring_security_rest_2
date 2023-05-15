package com.example.spring_security_rest_2.controller;

import com.example.spring_security_rest_2.dto.request.AuthenticationRequest;
import com.example.spring_security_rest_2.dto.response.AuthenticationResponse;
import com.example.spring_security_rest_2.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {


    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authRequest){
        AuthenticationResponse response = authService.login(authRequest);
        if(response.isSuccess()){
            return ResponseEntity.ok(response);
        }else {
//            return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(response);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}
