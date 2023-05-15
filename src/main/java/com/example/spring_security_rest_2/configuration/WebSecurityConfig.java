package com.example.spring_security_rest_2.configuration;

//import com.example.demo_spring_2.handler.CustomAuthenticationFailureHandler;

import com.example.spring_security_rest_2.security.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {


    @Autowired
    @Qualifier("currentUserDetailServiceImpl")
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationTokenFilter authenticationTokenFilter;

//
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()

                .authorizeHttpRequests()
                .requestMatchers("/auth/**").permitAll()
//                .requestMatchers("/admin/**").hasAuthority("ADMIN")
                .anyRequest()
                .authenticated();


//                .failureHandler(new CustomAuthenticationFailureHandler())

//                .and()
//                .exceptionHandling().accessDeniedHandler(accessDeniedHandler())
        http.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

//    @Bean
//    public AccessDeniedHandler accessDeniedHandler(){
//        return new CustomAccessDeniedHandler();
//    }

}