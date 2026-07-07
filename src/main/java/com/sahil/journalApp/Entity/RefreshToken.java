package com.sahil.journalApp.Entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "refresh_tokens")
@Data
public class RefreshToken {

    @Id
    private String id;
    private String token;
    @DBRef
    private User user;
    private Instant expiryDate;
}
