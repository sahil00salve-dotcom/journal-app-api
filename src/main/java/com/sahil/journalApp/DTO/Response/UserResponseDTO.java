package com.sahil.journalApp.DTO.Response;

import com.sahil.journalApp.Entity.JournalEntry;
import lombok.Data;

import java.util.List;

@Data
public class UserResponseDTO {

    private String id;

    private String username;

    private List<String> roles;

    private List<JournalResponseDTO> journalentries;
}