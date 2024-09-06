package com.example.shop.security.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.example.shop.security.token.JwtService;
import com.example.shop.security.token.TokenRepository;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final UserDetailsService userDetailsService;
    @Autowired
    private final TokenRepository tokenRepository;
    
    @Autowired
    @Qualifier("handlerExceptionResolver")
    private final HandlerExceptionResolver exceptionResolver;
    
    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService,
            TokenRepository tokenRepository, HandlerExceptionResolver exceptionResolver) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.tokenRepository = tokenRepository;
        this.exceptionResolver = exceptionResolver;
    }

    public JwtAuthenticationFilter(){
        this.jwtService = null;
        this.userDetailsService = null;
        this.tokenRepository = null;
        this.exceptionResolver = null;
    }
    
    public JwtAuthenticationFilter(HandlerExceptionResolver exceptionResolver) {
        this(null,null,null,exceptionResolver);
    }

    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain) throws ServletException, IOException {
     
            // if (request.getServletPath().contains("/api/v1/auth")) {
            //     System.out.println("jjjjj");
            //     filterChain.doFilter(request, response);
            //     return;
            // }
            
            final String authHeader = request.getHeader("Authorization");
            final String jwt;
            final String username;

            if(authHeader == null || !authHeader.startsWith("Bearer ")){
                filterChain.doFilter(request, response);
                return;
            }

            try {
                jwt = authHeader.substring(7);
                username = jwtService.extractUsername(jwt);
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                System.out.println(userDetails);
                if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                    var isTokenVaild = tokenRepository.findByToken(jwt)
                        .map(t -> !t.isExpired() && !t.isRevoked())
                        .orElse(false);
                    if (jwtService.isTokenValid(jwt, userDetails) && isTokenVaild) {
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                        );
                        authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                        );
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }

            } catch (Exception ex) {
                System.out.println("Rami");
                exceptionResolver.resolveException(request, response, null, ex);
            }
            filterChain.doFilter(request, response);    
            
    }
}
