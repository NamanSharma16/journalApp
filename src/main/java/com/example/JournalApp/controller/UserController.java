package com.example.JournalApp.controller;

import com.example.JournalApp.entities.User;
import com.example.JournalApp.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public Mono<ResponseEntity<User>> createUser(@RequestBody User user){
        return userService.saveUser(user)
                .map(savedEntry ->
                        status(HttpStatus.CREATED)
                                .body(savedEntry));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Flux<User>>> getAllUsers(){
        Flux<User> cachedFlux = userService.getAllUsers().cache();
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

    @GetMapping("/getUser")
    public Mono<ResponseEntity<User>> findUser(@RequestBody User user){
        return userService.getUserInfo(user)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .build()
                );
    }


    @PutMapping("/update-user/{username}")
    public Mono<ResponseEntity<User>> updateUserInfo(@RequestBody User user, @PathVariable String username){
        return userService.findByUserName(username)
                .flatMap(existingUser ->
                {
                    if(existingUser == null) return Mono.empty();
                    existingUser.setPassword(!user.getPassword().equals(existingUser.getPassword()) && !user.getPassword().isEmpty() ?
                            user.getPassword() : existingUser.getPassword());
                    existingUser.setUsername(!existingUser.getUsername().equals(user.getUsername()) && !user.getUsername().isEmpty() ?
                            user.getUsername() : existingUser.getUsername());
                    return userService.saveUser(existingUser);
                }).map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .build());
    }

    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<?> deleteUser(@RequestBody User user, @PathVariable ObjectId id){
        userService.deleteUser(id);
        return status(HttpStatus.NO_CONTENT).body("Deleted Successfully");
    }

}
