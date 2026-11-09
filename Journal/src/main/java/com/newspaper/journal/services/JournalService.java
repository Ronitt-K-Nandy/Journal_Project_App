package com.newspaper.journal.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.newspaper.journal.entities.UserEntity;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.newspaper.journal.entities.Journal;
import com.newspaper.journal.repositories.JournalRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JournalService {

    private JournalRepository journalRepository;
    private UserService userService;

    public JournalService(JournalRepository journalRepository, UserService userService){
        this.journalRepository=journalRepository;
        this.userService=userService;
    }

    //There are two ways of generating logs, one is LoggerFactory.logger and another is @Slf4j
    // using it as for example, logger.info and lof.info respectively.
    //Check UserService.java for that.
    private static final Logger logger = LoggerFactory.getLogger(JournalService.class);

    //PlatformTransactionManager- This interface is responsible for committing, rollback actions.
    //MongoTransactionManager - For MongoDB, we need to use MongoTransactionManager as the implementation of PlatformTransactionManager.
    @Transactional
    public Journal createEntryByUsername(Journal journal, UserEntity user)
    {
        logger.info("Printed at Run");
        logger.warn("Printed at Run");
        logger.error("Printed at Run");
        logger.debug("Not printed at Run");
        logger.trace("Not printed at Run");
        try {
            journal.setDate(LocalDateTime.now());
            Journal savedJournal = journalRepository.save(journal);
            user.getJournalEntries().add(savedJournal);
            userService.createEntry(user);
            return savedJournal;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Journal> getJournalById(ObjectId id) {
        return journalRepository.findById(id);
    }

    @Transactional
    public boolean removeJournalByUserNameAndJournalName(String userName, String journalTitle){
        Optional<UserEntity> user = userService.getUserByUsername(userName);
        if(user.isPresent())
        {
            Optional<Journal> journalByTitle = Optional.ofNullable(getJournalByTitle(journalTitle));

                if (!journalByTitle.isEmpty() && user.get().getJournalEntries().stream().anyMatch(i -> i.getId().equals(journalByTitle.get().getId()))) {
                    user.get().getJournalEntries().removeIf(i -> i.getTitle().equals(journalTitle));
                    try {
                        userService.createEntry(user.get());
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to disassociate journal from user");
                    }
                    journalRepository.deleteById(journalByTitle.get().getId());
                }
                    return true;
        }
        return false;
    }

    @Transactional
    public boolean removeJournalByUserNameAndJournalId(String userName, ObjectId id){
        Optional<UserEntity> user = userService.getUserByUsername(userName);
        if(user.isPresent() && user.get().getJournalEntries().stream().anyMatch(i -> i.getId().equals(id)))
        {
            user.get().getJournalEntries().removeIf(i -> i.getId().equals(id));
            try {
                userService.createEntry(user.get());
            } catch (Exception e) {
                throw new RuntimeException("Failed to disassociate journal from user");
            }
            journalRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private Journal getJournalByTitle(String journalTitle) {
        return journalRepository.getJournalByTitle(journalTitle);
    }

    public List<Journal> getAllJournals() {
        return journalRepository.findAll();
    }
}
