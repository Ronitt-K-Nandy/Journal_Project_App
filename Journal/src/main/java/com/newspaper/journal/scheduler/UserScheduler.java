package com.newspaper.journal.scheduler;

import com.newspaper.journal.entities.Journal;
import com.newspaper.journal.entities.UserEntity;
import com.newspaper.journal.repositories.UserRepository;
import com.newspaper.journal.services.EmailService;
import com.newspaper.journal.services.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class UserScheduler {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;
    @Autowired
    private EmailService emailService;

    @Scheduled(cron = "0 * * * * *") // get cron expression generated, from: https://www.w3schools.com/tools/tool_cron_builder.php
    public void fetchUsersAndSendsentimentAnalysisEmails(){
        //  To test for @Scheduled, you need to change the cron expression,
        //  and, also you need to turn the debug mode on instead of run, and, it will be debugged automatically
        //according to the cron expression timing.
        for(UserEntity user : userRepository.findAll())
        {
            List<String> filteredJournals = user.getJournalEntries().stream().filter(journal -> journal.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(journal -> journal.getContent()).toList();
            String entry = String.join(" ",filteredJournals);
            String sentiment = sentimentAnalysisService.getSentiment(entry);
            emailService.sendEmail(user.getEmail(), "Sentiment Analysis", "Your sentiment analysis report is ready. The sentiment for last 7 days is: " + sentiment);
        }
    }
}
