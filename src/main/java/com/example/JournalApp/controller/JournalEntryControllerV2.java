package com.example.JournalApp.controller;

import com.example.JournalApp.entities.JournalEntry;
import com.example.JournalApp.services.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @PostMapping
    public Mono<ResponseEntity<JournalEntry>> createEntry(@RequestBody JournalEntry entry){
        entry.setDate(LocalDateTime.now());
        return journalEntryService.saveEntry(entry)
                .map(savedEntry ->
                        status(HttpStatus.CREATED)
                                .body(savedEntry));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Flux<JournalEntry>>> getAll(){
        Flux<JournalEntry> cachedFlux = journalEntryService.getEntries().cache();
        return cachedFlux.hasElements()
                .flatMap(hasElements -> hasElements
                        ? Mono.just(
                                ResponseEntity.ok()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .body(cachedFlux))
                        : Mono.just(
                                ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .build())
                );
    }

    @GetMapping(path = "id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<JournalEntry>> findJournalById(@PathVariable ObjectId id){
        return journalEntryService.getEntry(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .build()
                );
    }


    @PutMapping("/id/{id}")
    public Mono<ResponseEntity<JournalEntry>> editJournalById(@PathVariable ObjectId id, @RequestBody JournalEntry entry){
        return journalEntryService.editEntry(id, entry)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .build());
    }

    @DeleteMapping("id/{id}")
    public ResponseEntity<?> deleteJournalByID(@PathVariable ObjectId id){
        journalEntryService.deleteEntry(id);
        return status(HttpStatus.NO_CONTENT).body("ID : " + id.toString() + " Deleted Successfully");
    }

}
