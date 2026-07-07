package com.sahil.journalApp.Entity;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Document("users")
public class User {


    @Id
    private String id;

    @Indexed(unique = true)
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private List<String> roles;

    private boolean enabled = true;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


    @DBRef
    private List<JournalEntry> journalEntries=new ArrayList<>();
}
