package com.example.JournalApp.repository;

import com.example.JournalApp.entities.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, ObjectId> {

    public Mono<User> findByUsername(String username);
}
