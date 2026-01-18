package com.example.JournalApp.repository;

import com.example.JournalApp.entities.ConfigConstants;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigConstantsRepository extends MongoRepository<ConfigConstants, ObjectId> {
}
