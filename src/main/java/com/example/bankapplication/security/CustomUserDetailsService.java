package com.example.bankapplication.security;

import com.example.bankapplication.entity.Client;
import com.example.bankapplication.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Client client = clientRepository.findClientByEmail(email);
        List<String> roles = new ArrayList<>();
        //roles.add("USER");
        roles.add(client.getStatus().name());
        return org.springframework.security.core.userdetails.User.builder()
                        .username(client.getEmail())
                        .password(client.getPass())
                        .roles(roles.toArray(new String[0]))
                        .build();
    }
}
