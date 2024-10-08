package com.example.e_commerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.e_commerce.filter.JwtAuthFilter;
import com.example.e_commerce.service.UserDetailsServiceImp;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserDetailsServiceImp userDetailsServiceImp;
    
    private final JwtAuthFilter jwtAuthFilter;

    
        

    public SecurityConfig(UserDetailsServiceImp userDetailsServiceImp, JwtAuthFilter jwtAuthFilter) {
        this.userDetailsServiceImp = userDetailsServiceImp;
        this.jwtAuthFilter = jwtAuthFilter;
    }


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception{

        return http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(req->
            req.requestMatchers("/login/**","/register/**")
            .permitAll()
            .requestMatchers("/admin/**")
            .hasAuthority("ADMIN")
            .requestMatchers("/product/**")
            .permitAll()
            .anyRequest()
            .authenticated()
        )
        .userDetailsService(userDetailsServiceImp)
        .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(jwtAuthFilter,UsernamePasswordAuthenticationFilter.class)
        .build();
    }


    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{

        return configuration.getAuthenticationManager();
    }
}
