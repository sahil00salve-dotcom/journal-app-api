package com.sahil.journalApp.DTO.Response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class JournalResponseDTO {

    private String id;

    private String title;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}