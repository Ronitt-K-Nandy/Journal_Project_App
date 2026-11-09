package com.newspaper.journal.controllers;

import com.newspaper.journal.entities.Journal;
import com.newspaper.journal.entities.UserEntity;
import com.newspaper.journal.repositories.JournalRepository;
import com.newspaper.journal.services.JournalService;
import com.newspaper.journal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/public")
public class PublicController {

    private UserService userService;
    private JournalService journalService;


    public PublicController(UserService userService, JournalService journalService){
        this.userService=userService;
        this.journalService=journalService;
    }
    @PostMapping("/createUser")
    public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity userEntity)
    {
        try{

            UserEntity createdUserEntity = userService.encodePasswordAndCreateEntry(userEntity);
            return new ResponseEntity<>(createdUserEntity,HttpStatus.CREATED);
        }catch(Exception e)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAllJournals")
    public List<Journal> getAllJournals() {
        return journalService.getAllJournals();
    }
}