package com.example.spring_security_rest_2.service;

import com.example.spring_security_rest_2.dto.request.AuthenticationRequest;
import com.example.spring_security_rest_2.dto.response.AuthenticationResponse;

public interface AuthService {

    public AuthenticationResponse login(AuthenticationRequest authRequest);
}
