package com.example.JournalApp.controller;

import com.example.JournalApp.entities.User;
import com.example.JournalApp.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers() {
        List<User> all = userService.getAllUsers();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/add-admin-user")
    public ResponseEntity<User> addNewAdminUser(@RequestBody User user) {
        try{
            return new ResponseEntity<>(userService.saveNewAdminUser(user), HttpStatus.CREATED);
        }catch (Exception e){
            log.debug("User creation encountered error");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}

