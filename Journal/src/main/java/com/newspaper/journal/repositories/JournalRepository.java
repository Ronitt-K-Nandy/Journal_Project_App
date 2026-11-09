package com.newspaper.journal.repositories;

import com.newspaper.journal.entities.Journal;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalRepository extends MongoRepository<Journal, ObjectId>{

    Journal getJournalByTitle(String title);
}
