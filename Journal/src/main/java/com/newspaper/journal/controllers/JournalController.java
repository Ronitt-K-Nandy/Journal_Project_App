package com.newspaper.journal.controllers;

import java.util.List;
import java.util.Optional;

import com.newspaper.journal.entities.UserEntity;
import com.newspaper.journal.repositories.JournalRepository;
import com.newspaper.journal.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.newspaper.journal.entities.Journal;
import com.newspaper.journal.services.JournalService;

import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/journal")
public class JournalController {

    //@Autowired
    private JournalService journalService;
    //@Autowired
    private UserService userService;
    //@Autowired
    private JournalRepository journalRepository;

    public JournalController(JournalService journalService,UserService userService,JournalRepository journalRepository){
        this.journalService=journalService;
        this.userService=userService;
        this.journalRepository=journalRepository;
    }

    @GetMapping
    public ResponseEntity<List<Journal>> getAllJournalsByUser() {
        //When multiple format inside ResponseEntity, use '?' of 'List<Journal>'
        Optional<UserEntity> user = getUserNameAfterAuthentication();
        if(user.isPresent()) {
            List<Journal> allEntries = user.get().getJournalEntries();
            if (!allEntries.isEmpty()) {
                return new ResponseEntity<>(allEntries, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Journal> createEntry(@RequestBody Journal journal) {
        Optional<UserEntity> user = getUserNameAfterAuthentication();
        if(user.isPresent()) {
            Journal createdJournal = journalService.createEntryByUsername(journal, user.get());
            return new ResponseEntity<>(createdJournal, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<Journal> updateJournal(@PathVariable ObjectId id, @RequestBody Journal journal)
    {
        Optional<UserEntity> user = getUserNameAfterAuthentication();
            if(user.isPresent() && user.get().getJournalEntries().stream().anyMatch(i-> i.getId().equals(id))) {
                Optional<Journal> oldJournal = journalService.getJournalById(id);
                if (oldJournal.isPresent()) {
                    oldJournal.get().setContent(journal.getContent());
                    oldJournal.get().setTitle(journal.getTitle());
                    Journal updatedJournal = journalService.createEntryByUsername(oldJournal.get(),user.get());
                    return new ResponseEntity<>(updatedJournal, HttpStatus.CREATED);
                }
            }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Journal> getJournalById(@PathVariable ObjectId id) {
        Optional<UserEntity> user = getUserNameAfterAuthentication();
        if(user.isPresent())
        {
            for(Journal i:user.get().getJournalEntries())
            {
                if(i.getId().equals(id))
                {
                    Optional<Journal> journalFound = journalService.getJournalById(id);
                    if(journalFound.isPresent())
                    {
                        return new ResponseEntity<>(journalFound.get(), HttpStatus.OK);
                    }
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{journalTitle}")
    public ResponseEntity<Void> removeJournalByUserName(@PathVariable String journalTitle){
        Optional<UserEntity> user = getUserNameAfterAuthentication();
        if(user.isPresent()) {
            try {
                journalService.removeJournalByUserNameAndJournalName(user.get().getUsername(),journalTitle);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> removeJournalByJournalId(@PathVariable ObjectId id){
        Optional<UserEntity> user = getUserNameAfterAuthentication();
        if(user.isPresent()) {
            try {
                if(journalService.removeJournalByUserNameAndJournalId(user.get().getUsername(), id))
                    return new ResponseEntity<>(HttpStatus.OK);
                else
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteAllJournals")
    public ResponseEntity<Void> removeAllJournalByUserName(){
        Optional<UserEntity> user = getUserNameAfterAuthentication();
        if(user.isPresent()) {
            try {
                for(Journal i:user.get().getJournalEntries())
                {
                    journalRepository.deleteById(i.getId());
                }

                user.get().getJournalEntries().removeIf(i -> true);
                userService.createEntry(user.get());

                return new ResponseEntity<>(HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    private Optional<UserEntity> getUserNameAfterAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName;
        if(authentication!=null)
            userName = authentication.getName();
        else
            throw new NullPointerException("Authentication could not be done");
        Optional<UserEntity> user = userService.getUserByUsername(userName);
        return user;
    }
}