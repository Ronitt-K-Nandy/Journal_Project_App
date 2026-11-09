package com.Newspaper.Journal.repository;

import com.newspaper.journal.repositories.impl.UserRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepositoryImplTest {

    @Autowired
    private UserRepositoryImpl userRepositoryImpl;
    @Test
    public void testSavingNewUser(){
        userRepositoryImpl.getUsersForSentimentAnalysis();
    }
}