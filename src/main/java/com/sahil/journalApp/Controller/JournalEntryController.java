package com.sahil.journalApp.Controller;

import com.sahil.journalApp.DTO.Request.JournalRequestDTO;
import com.sahil.journalApp.Entity.JournalEntry;
import com.sahil.journalApp.Entity.User;
import com.sahil.journalApp.Mapper.JournalEntryMapper;
import com.sahil.journalApp.Mapper.UserMapper;
import com.sahil.journalApp.Service.JournalEntryService;
import com.sahil.journalApp.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
@Slf4j
@Tag(name = "Journal APIs", description = "Operations related to Journal Entries")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private JournalEntryMapper journalEntryMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<List<JournalEntry>> getAllJournalEntries() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        log.info("Fetching all journal entries for user: {}", username);

        User user = userService.findByUsername(username);
        List<JournalEntry> journalEntries = user.getJournalEntries();

        if (journalEntries != null && !journalEntries.isEmpty()) {
            log.info("Successfully fetched {} journal entries for user: {}",
                    journalEntries.size(), username);
            return ResponseEntity.ok(journalEntries);
        }

        log.warn("No journal entries found for user: {}", username);
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/id/{journalId}")
    @Operation(summary = "Get journal entry by id")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable String journalId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        log.info("Fetching journal {} for user: {}", journalId, username);

        User user = userService.findByUsername(username);

        Optional<JournalEntry> journalEntry = user.getJournalEntries()
                .stream()
                .filter(x -> x.getId().equals(journalId))
                .findFirst();

        if (journalEntry.isPresent()) {
            log.info("Journal {} fetched successfully for user: {}", journalId, username);
            return ResponseEntity.ok(journalEntry.get());
        }

        log.warn("Journal {} not found for user: {}", journalId, username);
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a journal entry")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<JournalEntry> createJournalEntry(
            @Valid @RequestBody JournalRequestDTO dto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        log.info("Creating journal entry for user: {}", username);

        JournalEntry journalEntry = journalEntryMapper.toEntity(dto);

        JournalEntry saved = journalEntryService.saveJournalEntry(journalEntry, username);

        log.info("Journal created successfully with id: {} for user: {}",
                saved.getId(), username);

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/id/{journalId}")
    @Operation(summary = "Delete journal entry by id")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<JournalEntry> deleteJournalEnteryById(
            @PathVariable String journalId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        log.info("Deleting journal {} for user: {}", journalId, username);

        User user = userService.findByUsername(username);

        Optional<JournalEntry> journalEntry = user.getJournalEntries()
                .stream()
                .filter(x -> x.getId().equals(journalId))
                .findFirst();

        if (journalEntry.isPresent()) {

            journalEntryService.deleteById(journalId, username);

            log.info("Journal {} deleted successfully for user: {}", journalId, username);

            return ResponseEntity.noContent().build();
        }

        log.warn("Delete failed. Journal {} not found for user: {}", journalId, username);

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/id/{id}")
    @Operation(summary = "Update journal entry by id")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<JournalEntry> updateJournalEntryById(
            @Valid @RequestBody JournalRequestDTO dto,
            @PathVariable String id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        log.info("Updating journal {} for user: {}", id, username);

        JournalEntry newJournalEntry = journalEntryMapper.toEntity(dto);

        User user = userService.findByUsername(username);

        Optional<JournalEntry> journalEntry = user.getJournalEntries()
                .stream()
                .filter(x -> x.getId().equals(id))
                .findFirst();

        if (journalEntry.isPresent()) {

            JournalEntry oldJournalEntry = journalEntry.get();

            oldJournalEntry.setTitle(
                    newJournalEntry.getTitle() != null && !newJournalEntry.getTitle().isEmpty()
                            ? newJournalEntry.getTitle()
                            : oldJournalEntry.getTitle());

            oldJournalEntry.setContent(
                    newJournalEntry.getContent() != null && !newJournalEntry.getContent().isEmpty()
                            ? newJournalEntry.getContent()
                            : oldJournalEntry.getContent());

            oldJournalEntry.setUpdatedAt(LocalDateTime.now());

            JournalEntry updatedEntry = journalEntryService.updateJournalEntry(oldJournalEntry);

            log.info("Journal {} updated successfully for user: {}", id, username);

            return ResponseEntity.ok(updatedEntry);
        }

        log.warn("Update failed. Journal {} not found for user: {}", id, username);

        return ResponseEntity.notFound().build();
    }
}