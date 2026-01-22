package com.example.JournalApp.service;

import com.example.JournalApp.entities.User;
import com.example.JournalApp.repository.UserRepositoryImpl;
import com.example.JournalApp.services.EmailService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

@SpringBootTest
public class EmailServiceTest {

    private final EmailService emailService;
    private final MongoTemplate mongoTemplate;
    private final UserRepositoryImpl userRepository;

    @Autowired
    public EmailServiceTest(EmailService emailService, MongoTemplate mongoTemplate, UserRepositoryImpl userRepository) {
        this.emailService = emailService;
        this.mongoTemplate = mongoTemplate;
        this.userRepository = userRepository;
    }

    @Test
    @Disabled
    void testSendEmail(){
        Query query = new Query();
        query.addCriteria(Criteria.where("email").regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"));
        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));
        List<String> emails = mongoTemplate.find(query, User.class).stream().map(User::getEmail).toList();
        emails.forEach(email->
                emailService.sendEmail(email,"from journal app","hi sir ..testing his email service"));
    }
}
