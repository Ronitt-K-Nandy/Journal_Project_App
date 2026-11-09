package com.Newspaper.Journal.repository;

import com.newspaper.journal.repositories.impl.UserRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserRepositoryImplTest {

    @Autowired
    private UserRepositoryImpl userRepositoryImpl;
    @Test
    public void testSavingNewUser(){
        assertNotNull(userRepositoryImpl.getUsersForSentimentAnalysis());
    }
}