package com.newspaper.journal.repositories;

import com.newspaper.journal.entities.UserEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, ObjectId>{

    UserEntity findUserByUsername(String username);

    void deleteUserByUsername(String username);
}
