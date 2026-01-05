package com.example.JournalApp.service;

import com.example.JournalApp.repository.UserRepository;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class UserServiceTests {


    @Autowired
    private UserRepository userRepository;

//    @ParameterizedTest
//    @CsvSource({
//            "ram",
//            "naman"
//    })
//    void testFindByUsername(String name){
//        assertNotNull(userRepository.findByUsername(name));
//    }


}
