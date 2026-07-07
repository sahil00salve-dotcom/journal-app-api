package com.sahil.journalApp.Controller;

import com.sahil.journalApp.DTO.Request.UserRequestDTO;
import com.sahil.journalApp.Entity.User;
import com.sahil.journalApp.Mapper.UserMapper;
import com.sahil.journalApp.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Slf4j
@Tag(name = "User APIs")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @DeleteMapping
    @Operation(summary = "Delete User Information by username")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> deletebyusername() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        log.info("Delete account request received for user: {}", username);

        User user = userService.findByUsername(username);

        if (user != null) {

            userService.deleteByusername(username);

            log.info("User account deleted successfully: {}", username);

            return ResponseEntity.noContent().build();
        }

        log.warn("Delete failed. User not found: {}", username);

        return ResponseEntity.notFound().build();
    }

    @PutMapping
    @Operation(summary = "Update User Information")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserRequestDTO dto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        log.info("Update profile request received for user: {}", username);

        User user = userMapper.toEntity(dto);

        User userInDb = userService.findByUsername(username);

        if (userInDb != null) {

            userInDb.setUsername(user.getUsername());
            userInDb.setPassword(user.getPassword());

            userService.saveUser(userInDb);

            log.info("User profile updated successfully: {}", userInDb.getUsername());

            return ResponseEntity.ok().build();
        }

        log.warn("Update failed. User not found: {}", username);

        return ResponseEntity.notFound().build();
    }
}