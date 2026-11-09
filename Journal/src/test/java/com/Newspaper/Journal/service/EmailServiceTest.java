package com.Newspaper.Journal.service;

import com.newspaper.journal.services.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;
    @Test
    public void testEmailSending(){
        emailService.sendEmail("ranitnandy10@gmail.com",
                           "Sending email from Journal App",
                             "This is a test email from Journal App");
    }
}