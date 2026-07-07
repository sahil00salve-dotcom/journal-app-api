package com.sahil.journalApp.Mapper;

import com.sahil.journalApp.DTO.Request.UserRequestDTO;
import com.sahil.journalApp.DTO.Response.UserResponseDTO;
import com.sahil.journalApp.Entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
uses = JournalEntryMapper.class)
public interface UserMapper {

    User toEntity(UserRequestDTO dto);
    UserResponseDTO toResponseDTO(User user);

}
