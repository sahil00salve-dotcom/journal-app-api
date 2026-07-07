package com.sahil.journalApp.Service;

import com.sahil.journalApp.Entity.RefreshToken;
import com.sahil.journalApp.Entity.User;
import com.sahil.journalApp.Exception.RefreshTokenExpiredException;
import com.sahil.journalApp.Repository.RefreshTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    public RefreshToken createRefreshToken(User user) {

        log.info("Creating refresh token for user: {}", user.getUsername());

        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plus(7, ChronoUnit.DAYS));

        RefreshToken savedToken = refreshTokenRepository.save(refreshToken);

        log.info("Refresh token created successfully for user: {}", user.getUsername());

        return savedToken;
    }

    public RefreshToken findByToken(String token) {

        log.info("Searching for refresh token");

        return refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> {
                    log.warn("Refresh token not found");
                    return new RuntimeException("Refresh token not found");
                });
    }

    public Optional<RefreshToken> findByUser(User user) {

        log.info("Fetching refresh token for user: {}", user.getUsername());

        return refreshTokenRepository.findByUser(user);
    }

    public void deleteByUser(User user) {

        log.info("Deleting refresh token for user: {}", user.getUsername());

        refreshTokenRepository.deleteByUser(user);

        log.info("Refresh token deleted successfully for user: {}", user.getUsername());
    }

    public boolean existsByToken(String token) {

        log.info("Checking if refresh token exists");

        return refreshTokenRepository.existsByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken refreshToken) {

        log.info("Verifying refresh token expiration");

        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {

            log.warn("Refresh token expired for user: {}", refreshToken.getUser().getUsername());

            refreshTokenRepository.delete(refreshToken);

            throw new RefreshTokenExpiredException(
                    "Refresh token has expired. Please login again.");
        }

        log.info("Refresh token is valid");

        return refreshToken;
    }

    public void deleteByToken(String token) {

        log.info("Deleting refresh token");

        refreshTokenRepository.deleteByToken(token);

        log.info("Refresh token deleted successfully");
    }
}