package com.kapil.employeeRestDemo.service;

import com.kapil.employeeRestDemo.model.JwtPayload;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;


// OncePerRequestFilter will execute only once per request within a single request thread.
public class JwtFilter extends OncePerRequestFilter {

    ObjectMapper objectMapper = new ObjectMapper();
    private final JwtServiceImpl jwtService;
    private final CustomUserDetailsServiceImpl userDetailsService;

    public JwtFilter(JwtServiceImpl jwtService, CustomUserDetailsServiceImpl userDetailsService){
        this.jwtService = jwtService;
        this.userDetailsService =userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Check if HttpServletRequest has Authorization passed in header
        String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }

        String[] tokenParts = authHeader.replace("Bearer ","")
                .split("\\.");

        if(tokenParts.length!=3){
            filterChain.doFilter(request,response);
            return;
        }

        String header = tokenParts[0];
        String payload = tokenParts[1];
        String signature = tokenParts[2];

        String encodedHeaderAndPayload = header+"."+payload;

        try {
            String expectedSignature = jwtService.sign(encodedHeaderAndPayload);

            if(!expectedSignature.equals(signature)){
                filterChain.doFilter(request,response);
                return;
            }

            String decodedPayload = new String(Base64.getUrlDecoder().decode(payload), StandardCharsets.UTF_8);

            // Create object of jwtPayload to check if token has expired or not.
            JwtPayload jwtPayload = objectMapper.readValue(decodedPayload, JwtPayload.class);

            if(jwtPayload.getExp()<= Instant.now().getEpochSecond()){
                filterChain.doFilter(request,response);
                return;
            }

            UserDetails userDetails = userDetailsService.loadUserByUsername(jwtPayload.getSub());

            // Delegates request to DaoAuthenticationManager to check if the user is authenticated or not. DaoAuthenticationManager checks with UserDetails to load and return User where the stored password in db is matched with one sent from postman.
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities() );
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        filterChain.doFilter(request,response);
    }
}

