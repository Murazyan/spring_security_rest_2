package com.example.spring_security_rest_2.security;


import com.example.spring_security_rest_2.model.User;
import com.example.spring_security_rest_2.repository.UserRepository;
import com.example.spring_security_rest_2.util.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;
import java.util.Optional;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Qualifier("currentUserDetailServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserRepository userRepository;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException, ServletException {
        response.setHeader("Access-Control-Allow-Origin", "Authorization");
        response.setHeader("Access-Control-Allow-Headers", "Authorization");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setHeader("Access-Control-Allow-Origin", "*");
        final String requestHeader = request.getHeader(this.tokenHeader);
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            String token = requestHeader.substring(7);
            String emailFromToken = jwtTokenUtil.getEmailFromToken(token);
            Optional<User> userFromDbOptional = userRepository.findByEmail(emailFromToken);
            if (userFromDbOptional.isPresent()) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(emailFromToken);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(request, response);
    }
}