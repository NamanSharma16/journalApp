package com.example.JournalApp.services;

import com.example.JournalApp.entities.JournalEntry;
import com.example.JournalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public Mono<JournalEntry> saveEntry(JournalEntry journalEntry) {
        return journalEntryRepository.save(journalEntry);
    }

    public Flux<JournalEntry> getEntries(){
        return journalEntryRepository.findAll();
    }

    public Mono<JournalEntry> getEntry(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    public Mono<JournalEntry> editEntry(ObjectId id, JournalEntry entry){
        return getEntry(id).flatMap(old -> {
            if(old == null){
                return Mono.empty();
            }
            old.setTitle(entry.getTitle() != null && !entry.getTitle().isEmpty() ? entry.getTitle() : old.getTitle());
            old.setContent(entry.getContent() != null && !entry.getContent().isEmpty() ? entry.getContent() : old.getContent());
            return journalEntryRepository.save(old);
        });
    }

    public String deleteEntry(ObjectId id){
        journalEntryRepository.deleteById(id).subscribe();
        return "ID : " + id.toString() + " Deleted Successfully";
    }


}
