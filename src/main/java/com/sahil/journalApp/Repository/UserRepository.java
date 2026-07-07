package com.sahil.journalApp.Repository;

import com.sahil.journalApp.Entity.JournalEntry;
import com.sahil.journalApp.Entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
   Optional<User> findByusername(String name);
   Optional<User>deleteByusername(String username);
}
