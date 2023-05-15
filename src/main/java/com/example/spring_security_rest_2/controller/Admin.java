package com.example.spring_security_rest_2.controller;

import com.example.spring_security_rest_2.security.CurrentUser;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class Admin {

    @GetMapping("/test")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String test(@AuthenticationPrincipal CurrentUser currentUser){
        System.out.println("***** "+currentUser.getUser().getEmail());
        return "xxxxxx";
    }
}
