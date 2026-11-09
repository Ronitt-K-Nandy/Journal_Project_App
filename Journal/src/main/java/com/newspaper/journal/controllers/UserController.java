package com.newspaper.journal.controllers;

import com.newspaper.journal.entities.Journal;
import com.newspaper.journal.entities.UserEntity;
import com.newspaper.journal.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/user")
public class UserController {

    //@Autowired
    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<UserEntity> getUserByUserName() {
        Optional<UserEntity> userFound = getUserNameAfterAuthentication();
        if(userFound.isPresent()){
            return new ResponseEntity<>(userFound.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<Journal> removeUserByUserName(){
        Optional<UserEntity> userFound = getUserNameAfterAuthentication();
        if(userFound.isPresent()) {
            try {
                userService.removeUserByUsername(userFound.get().getUsername());
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/removeUser")
    public ResponseEntity<?> removeUserByUsernameByAdmin(@RequestParam String username){
        Optional<UserEntity> userFound = getUserNameAfterAuthentication();
        if(userFound.isPresent()) {
            UserEntity userEntity = userFound.get();
            if(userEntity.getRoles().contains("ADMIN") || username.equals(userEntity.getUsername())) {
                try {
                    userService.removeUserByUsername(username);
                    return new ResponseEntity<>(HttpStatus.OK);
                } catch (Exception e) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            }
            else
                return new ResponseEntity<>("The User does not have any \"ADMIN\" role associated with it, hence cannot delete any other user",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping
    public ResponseEntity<UserEntity> updateUser(@RequestBody UserEntity userEntity)
    {
        Optional<UserEntity> oldUser = getUserNameAfterAuthentication();
        if(oldUser.isPresent())
        {
            oldUser.get().setUsername(userEntity.getUsername());
            oldUser.get().setPassword(userEntity.getPassword());
            oldUser.get().setRoles(userEntity.getRoles());
            try {
                UserEntity updatedUserEntity = userService.encodePasswordAndCreateEntry(oldUser.get());
                return new ResponseEntity<>(updatedUserEntity, HttpStatus.CREATED);
            }catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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