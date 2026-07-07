package com.sahil.journalApp.DTO.Response;

import lombok.Data;

@Data
public class LoginResponseDTO {

    private String accessToken;

    private String refreshToken;

    private String tokenType = "Bearer";
}