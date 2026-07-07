package com.sahil.journalApp.Controller;

import com.sahil.journalApp.DTO.Request.RefreshTokenRequestDTO;
import com.sahil.journalApp.DTO.Request.UserRequestDTO;
import com.sahil.journalApp.DTO.Response.RefreshTokenResponseDTO;
import com.sahil.journalApp.Entity.RefreshToken;
import com.sahil.journalApp.Entity.User;
import com.sahil.journalApp.Mapper.UserMapper;
import com.sahil.journalApp.Service.RefreshTokenService;
import com.sahil.journalApp.Service.UserService;
import com.sahil.journalApp.Util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Slf4j
@Tag(name = "Authentication APIs")
public class AuthController {

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/Health-Check")
    public String health() {

        log.info("Health check endpoint called");

        return "Application is OK!";
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> register(@Valid @RequestBody UserRequestDTO dto) {

        log.info("Registration request received for username: {}", dto.getUsername());

        User user = userMapper.toEntity(dto);

        User isCreated = userService.registerUser(user);

        if (isCreated != null) {

            log.info("User registered successfully: {}", dto.getUsername());

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("User created successfully");
        }

        log.warn("Registration failed. Username already exists: {}", dto.getUsername());

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("Username already exists");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserRequestDTO dto) {

        log.info("Login request received for username: {}", dto.getUsername());

        User request = userMapper.toEntity(dto);

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getUsername(),
                                request.getPassword()
                        )
                );

        UserDetails userDetails =
                (UserDetails) authentication.getPrincipal();

        String accessToken = jwtUtil.generateToken(userDetails.getUsername());

        User user = userService.findByUsername(userDetails.getUsername());

        RefreshToken refreshToken =
                refreshTokenService.createRefreshToken(user);

        RefreshTokenResponseDTO response =
                new RefreshTokenResponseDTO();

        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken.getToken());

        log.info("User logged in successfully: {}", userDetails.getUsername());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(
            @Valid @RequestBody RefreshTokenRequestDTO request) {

        log.info("Refresh token request received");

        RefreshToken refreshToken =
                refreshTokenService.findByToken(request.getRefreshToken());

        refreshTokenService.verifyExpiration(refreshToken);

        String accessToken =
                jwtUtil.generateToken(
                        refreshToken.getUser().getUsername());

        RefreshTokenResponseDTO response =
                new RefreshTokenResponseDTO();

        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken.getToken());

        log.info("Access token refreshed successfully for user: {}",
                refreshToken.getUser().getUsername());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            @Valid @RequestBody RefreshTokenRequestDTO request) {

        log.info("Logout request received");

        refreshTokenService.deleteByToken(request.getRefreshToken());

        log.info("User logged out successfully");

        return ResponseEntity.ok("Logged out successfully");
    }
}