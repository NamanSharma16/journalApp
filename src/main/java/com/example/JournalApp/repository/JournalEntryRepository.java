package com.example.JournalApp.repository;

import com.example.JournalApp.entities.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface JournalEntryRepository extends ReactiveMongoRepository<JournalEntry, ObjectId> {

}
