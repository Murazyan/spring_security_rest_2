package com.example.spring_security_rest_2.dto.request;

import com.example.spring_security_rest_2.dto.response.AuthenticationResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {


    private String  email;
    private String  password;


}
