package com.newspaper.journal.services;

import com.newspaper.journal.entities.UserEntity;
import com.newspaper.journal.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,BCryptPasswordEncoder passwordEncoder){
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public UserEntity encodePasswordAndCreateEntry(UserEntity userEntity)
    {
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        log.info("The user entity is: ",userEntity);
        return createEntry(userEntity);
    }
    public UserEntity createEntry(UserEntity userEntity) //throws Exception
    {
        //Initially problem was coming, that password was not matching after createing Journals,
        //because in journal creations this method is being called indirectly.
        //So, it encodes the already encoded password, which is not correct.
        //So, doing the encoding part in PublicController.

        return userRepository.save(userEntity);
    }

    public Optional<UserEntity> getUserByUsername(String userName) {
        return userRepository.findUserByUsername(userName) != null ? Optional.of(userRepository.findUserByUsername(userName)) : Optional.empty();
    }

    public Boolean removeUserById(ObjectId id){
        userRepository.deleteById(id);
        return true;
    }

    public Boolean removeUserByUsername(String userName){
        userRepository.deleteUserByUsername(userName);
        return true;
    }
}
