package com.sahil.journalApp.DTO.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RefreshTokenRequestDTO {

    @NotBlank(message = "refreshToken can not be blank")
    private String refreshToken;
}