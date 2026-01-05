package com.example.JournalApp.controller;

import com.example.JournalApp.entities.User;
import com.example.JournalApp.repository.UserRepository;
import com.example.JournalApp.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

//    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<List<User>> getAllUsers(){
//        List<User> allUsers = userService.getAllUsers();
//        if(allUsers != null){
//            return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }



    @PutMapping
    public ResponseEntity<User> updateUserInfo(@RequestBody User user){
        //Authenticated user and password
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User userInDb = userService.findByUserName(username);
        if(userInDb != null){
            userInDb.setUsername(user.getUsername());
            userInDb.setPassword(!user.getPassword().isEmpty()
                    && !user.getPassword().equals(userInDb.getPassword()) ? user.getPassword() : userInDb.getPassword());
            return new ResponseEntity<>(userService.saveUser(userInDb), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        userRepository.deleteByUsername(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
