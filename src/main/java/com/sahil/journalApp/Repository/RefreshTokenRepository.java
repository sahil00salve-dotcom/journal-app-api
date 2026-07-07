package com.sahil.journalApp.Repository;

import com.sahil.journalApp.Entity.RefreshToken;
import com.sahil.journalApp.Entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends MongoRepository<RefreshToken, String> {
    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByUser(User user);


    void deleteByUser(User user);

    void deleteByToken(String token);

    boolean existsByToken(String token);
}
