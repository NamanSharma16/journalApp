package com.example.JournalApp.services;

import com.example.JournalApp.entities.User;
import com.example.JournalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Mono<User> saveUser(User user){
        return userRepository.save(user);
    }

    public Flux<User> getAllUsers(){
        return userRepository.findAll();
    }

    public Mono<User> getUserInfo(User user){
        return userRepository.findByUsername(user.getUsername())
                .filter(foundUser -> Objects.equals(foundUser.getUsername(), user.getUsername()));
    }

    public Mono<User> findByUserName(String username){
        return userRepository.findByUsername(username);
    }

    public void deleteUser(ObjectId id) {
        userRepository.deleteById(id);
    }
}
