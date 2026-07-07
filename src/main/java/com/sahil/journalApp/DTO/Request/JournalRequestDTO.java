package com.sahil.journalApp.DTO.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class JournalRequestDTO {

    @NotBlank(message = "Title cannot be blank")
    private String title;

    @NotBlank(message = "Message cannot be Blank")
    private String content;
}