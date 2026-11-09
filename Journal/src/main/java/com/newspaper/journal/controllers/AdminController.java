package com.newspaper.journal.controllers;

import com.newspaper.journal.entities.UserEntity;
import com.newspaper.journal.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/admin")
public class AdminController {

    //@Autowired
    private UserService userService;

    public AdminController(UserService userService){
        this.userService=userService;
    }


    @GetMapping("allUsers")
    public ResponseEntity<List<UserEntity>> getAllUsers() { //When mutliple format inside ResponseEntity, use '?'
        List<UserEntity> allEntries = userService.getAllUsers();
        if(!allEntries.isEmpty()){
            return new ResponseEntity<>(allEntries, HttpStatus.OK);
        }
        return new  ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}