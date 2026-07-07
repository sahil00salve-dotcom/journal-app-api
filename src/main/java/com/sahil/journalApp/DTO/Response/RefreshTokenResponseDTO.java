package com.sahil.journalApp.DTO.Response;

import lombok.Data;

@Data
public class RefreshTokenResponseDTO {

    private String accessToken;

    private String refreshToken;
}