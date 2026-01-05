package com.example.JournalApp.repository;

import com.example.JournalApp.entities.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {

    public User findByUsername(String username);

    public User deleteByUsername(String username);

}
