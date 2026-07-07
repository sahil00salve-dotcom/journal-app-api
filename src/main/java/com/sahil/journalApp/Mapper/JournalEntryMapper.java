package com.sahil.journalApp.Mapper;

import com.sahil.journalApp.DTO.Request.JournalRequestDTO;
import com.sahil.journalApp.DTO.Response.JournalResponseDTO;
import com.sahil.journalApp.Entity.JournalEntry;
import org.mapstruct.Mapper;


    @Mapper(componentModel = "spring")
    public interface JournalEntryMapper {

        JournalEntry toEntity(JournalRequestDTO dto);

        JournalResponseDTO toResponseDTO(JournalEntry journalEntry);
    }

