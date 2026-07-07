package com.sahil.journalApp.Mapper;

import com.sahil.journalApp.DTO.Request.RefreshTokenRequestDTO;
import com.sahil.journalApp.DTO.Response.RefreshTokenResponseDTO;
import com.sahil.journalApp.Entity.RefreshToken;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RefreshTokenMapper {

    @Mapping(target = "token", source = "refreshToken")
    RefreshToken toEntity(RefreshTokenRequestDTO dto);

    @Mapping(target = "refreshToken", source = "token")
    @Mapping(target = "accessToken", ignore = true)
    RefreshTokenResponseDTO toResponseDTO(RefreshToken refreshToken);
}