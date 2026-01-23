package com.example.JournalApp.scheduler;

import com.example.JournalApp.entities.JournalEntry;
import com.example.JournalApp.entities.User;
import com.example.JournalApp.repository.UserRepositoryImpl;
import com.example.JournalApp.services.EmailService;
import com.example.JournalApp.services.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class UserScheduler {

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;

    @Autowired
    private EmailService emailService;

//    @Scheduled(cron = "* */1 * * * *")
    public void fetchUsersAndSendSaMail() {
        List<User> users = userRepository.getUserForSA();
        for(User user : users){
            List<String> journalEntries = user.getJournalEntries().stream().filter(x->x.getDate().isAfter(LocalDateTime.now().minusDays(7)))
                    .map(JournalEntry::getContent).toList();
            String entry = String.join(" ", journalEntries);
            String sentiment = sentimentAnalysisService.getSentiment(entry);
//            emailService.sendEmail(user.getEmail(), "Sentiment for last 7 days ", sentiment);
        }
    }
}
