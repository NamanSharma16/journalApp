package com.example.JournalApp.controller;

import com.example.JournalApp.entities.User;
import com.example.JournalApp.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {

    @Autowired
    private UserService userService;

    @PostMapping("/create-user")
    public ResponseEntity<User> createUser(@RequestBody User user){
        try{
            return new ResponseEntity<>(userService.saveNewUser(user), HttpStatus.CREATED);
        }catch (Exception e){
            log.debug("User creation encountered error");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
