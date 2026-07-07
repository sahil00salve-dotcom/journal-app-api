package com.sahil.journalApp.Controller;

import com.sahil.journalApp.DTO.Request.UserRequestDTO;
import com.sahil.journalApp.Entity.JournalEntry;
import com.sahil.journalApp.Entity.User;
import com.sahil.journalApp.Mapper.UserMapper;
import com.sahil.journalApp.Service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@Slf4j
@Tag(name = "Admin APIs")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers() {

        log.info("Request received to fetch all users");
        List<User> userList= userService.getAllUsers();
        for( User user : userList)
        {
            List<JournalEntry> journalEntries=  user.getJournalEntries();
            System.out.println(journalEntries);
        }

        return ResponseEntity.ok(userList);
    }

    @PostMapping("/create-admin")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> createadmin(@Valid @RequestBody UserRequestDTO dto) {

        log.info("Admin creation request received for username: {}", dto.getUsername());

        User user = userMapper.toEntity(dto);

        User createdAdmin = userService.registerAdmin(user);

        log.info("Admin created successfully: {}", createdAdmin.getUsername());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdAdmin);
    }

    @GetMapping("/{username}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> findUser(@PathVariable String username) {

        log.info("Request received to fetch user: {}", username);

        User user = userService.findByUsername(username);

        log.info("User fetched successfully: {}", username);

        return ResponseEntity.ok(userMapper.toResponseDTO(user));
    }
}