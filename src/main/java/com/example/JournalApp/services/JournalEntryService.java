package com.example.JournalApp.services;

import com.example.JournalApp.entities.JournalEntry;
import com.example.JournalApp.entities.User;
import com.example.JournalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public JournalEntry saveNewEntry(JournalEntry journalEntry, String username) {
        try{
            User user = userService.findByUserName(username);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
//            user.setUsername(null); -- if this is there then transaction fails; put here to test transaction rollback
            userService.saveUser(user);
            return saved;
        }catch (Exception e){
            System.out.println("Error saving journal entry: " + e.getMessage());
            throw new RuntimeException("Error saving journal entry:" + e);
        }
    }
    public JournalEntry saveEntry(JournalEntry journalEntry) {
        return journalEntryRepository.save(journalEntry);
    }

    public Optional<JournalEntry> getEntryById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public boolean deleteEntry(ObjectId id, String username){
        boolean removed = false;
        try {
            User user = userService.findByUserName(username);
            removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if (removed) {
                userService.saveUser(user);
                journalEntryRepository.deleteById(id);
            }
        } catch(Exception e) {
            System.out.println(e);
            throw new RuntimeException("An error occurred while deleteing entry", e);
        }
        return removed;
    }


}
