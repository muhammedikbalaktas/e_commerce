package com.example.e_commerce.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.e_commerce.service.JwtService;
import com.example.e_commerce.service.UserDetailsServiceImp;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter{

    private final JwtService jwtService;
    private final UserDetailsServiceImp userDetailsService;


    public JwtAuthFilter(JwtService jwtService, UserDetailsServiceImp userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService=userDetailsService;
    }

    @Override
    protected void doFilterInternal(
        HttpServletRequest request, 
        HttpServletResponse response, 
        FilterChain filterChain)
            throws ServletException, IOException {
        
        String header=request.getHeader("Authorization");
        if(header==null || !header.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        String token=header.substring(7);

        String userName=jwtService.extractUsername(token);

        if(userName!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails=userDetailsService.loadUserByUsername(userName);

            
            if(jwtService.isValid(token, userDetails)){
                UsernamePasswordAuthenticationToken authToken=
                new UsernamePasswordAuthenticationToken
                (userDetails, null,userDetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
            
        }
        filterChain.doFilter(request, response);

    }

    
    
}
