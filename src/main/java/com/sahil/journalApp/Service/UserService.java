package com.sahil.journalApp.Service;

import com.sahil.journalApp.Entity.User;
import com.sahil.journalApp.Exception.UserNotFoundException;
import com.sahil.journalApp.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordencoder = new BCryptPasswordEncoder();

    public User registerUser(User user) {

        log.info("Registering new user: {}", user.getUsername());

        user.setPassword(passwordencoder.encode(user.getPassword()));
        user.setRoles(List.of("USER"));

        User savedUser = userRepository.save(user);

        log.info("User registered successfully: {}", savedUser.getUsername());

        return savedUser;
    }

    public User registerAdmin(User user) {

        log.info("Registering admin: {}", user.getUsername());

        user.setPassword(passwordencoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER", "ADMIN"));

        User savedAdmin = userRepository.save(user);

        log.info("Admin registered successfully: {}", savedAdmin.getUsername());

        return savedAdmin;
    }

    public User saveUser(User user) {

        log.info("Updating user: {}", user.getUsername());

        user.setPassword(passwordencoder.encode(user.getPassword()));

        User updatedUser = userRepository.save(user);

        log.info("User updated successfully: {}", updatedUser.getUsername());

        return updatedUser;
    }

    public List<User> getAllUsers() {

        log.info("Fetching all users");

        return userRepository.findAll();
    }

    public User findByUsername(String username) {

        log.info("Searching user: {}", username);

        return userRepository.findByusername(username)
                .orElseThrow(() -> {

                    log.warn("User not found: {}", username);

                    return new UserNotFoundException("User not found");
                });
    }

    public void deleteByusername(String username) {

        log.info("Deleting user: {}", username);

        User user = findByUsername(username);

        userRepository.delete(user);

        log.info("User deleted successfully: {}", username);
    }
}