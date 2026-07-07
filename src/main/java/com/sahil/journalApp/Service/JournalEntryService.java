package com.sahil.journalApp.Service;

import com.sahil.journalApp.DTO.Request.JournalRequestDTO;
import com.sahil.journalApp.Entity.JournalEntry;
import com.sahil.journalApp.Entity.User;
import com.sahil.journalApp.Exception.JournalEntryNotFoundException;
import com.sahil.journalApp.Repository.JournalEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public JournalEntry saveJournalEntry(JournalEntry journalEntry, String username) {
        log.error("Creating journal entry for user: {}", username);
        User user = userService.findByUsername(username);
        journalEntry.setCreatedAt(LocalDateTime.now());
        JournalEntry savedEntry = journalEntryRepository.save(journalEntry);
        log.info("Journal saved with id: {}", savedEntry.getId());
        user.getJournalEntries().add(savedEntry);
        userService.saveUser(user);
        log.info("Journal successfully linked to user: {}", username);
        return savedEntry;
    }

    public List<JournalEntry> getAll() {
        log.info("Fetching all journal entries");
        return journalEntryRepository.findAll();
    }

    public JournalEntry updateJournalEntry(JournalEntry journalEntry) {
        log.info("Updating journal with id: {}", journalEntry.getId());
        JournalEntry existing = findById(journalEntry.getId());

        existing.setTitle(journalEntry.getTitle());
        existing.setContent(journalEntry.getContent());
        existing.setUpdatedAt(LocalDateTime.now());
        JournalEntry updated=journalEntryRepository.save(existing);
        log.info("Journal updated successfully: {}", updated.getId());
        return updated;
    }

    public JournalEntry findById(String journalId) {
        log.info("Searching journal with id: {}", journalId);
        return journalEntryRepository.findById(journalId)
                .orElseThrow(()->{
                            log.warn("Journal not found with id: {}", journalId);
                            return new JournalEntryNotFoundException("Journal Not Found");}
                        );
    }

    @Transactional
    public void deleteById(String journalId, String username) {
        log.info("Deleting journal {} for user {}", journalId, username);
        User user = userService.findByUsername(username);
        JournalEntry journalEntry = findById(journalId);
        boolean removed = user.getJournalEntries()
                .removeIf(x -> x.getId().equals(journalId));
        if (!removed) {
            log.warn("Journal {} does not belong to user {}", journalId, username);
            throw new JournalEntryNotFoundException("Journal not found for this user");
        }
        journalEntryRepository.delete(journalEntry);
        userService.saveUser(user);
        log.info("Journal {} deleted successfully", journalId);
    }

}
