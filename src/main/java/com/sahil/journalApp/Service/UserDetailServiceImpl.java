package com.sahil.journalApp.Service;

import com.sahil.journalApp.Entity.User;
import com.sahil.journalApp.Exception.UserNotFoundException;
import com.sahil.journalApp.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("Authenticating user: {}", username);

        User user = userRepository.findByusername(username)
                .orElseThrow(() -> {
                    log.warn("Authentication failed. User not found: {}", username);
                    return new UserNotFoundException("User not found");
                });

        log.info("User authenticated successfully: {}", username);

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRoles().toArray(new String[0]))
                .build();
    }
}
