package com.example.e_commerce.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.e_commerce.repository.UserRepository;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    
    private final UserRepository userRepository;

    public UserDetailsServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        

        return userRepository.findByUsername(username).orElseThrow(
            ()-> new UsernameNotFoundException("user not found")
        );
    
    }
    
}
