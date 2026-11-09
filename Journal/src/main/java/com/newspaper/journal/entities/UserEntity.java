package com.newspaper.journal.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user_entries") //Specific to mongodb
public class UserEntity {

    @Id
    private ObjectId id;

    @Indexed(unique = true) //Specific to mongodb, S.Boot does not index automatically, so write in app.properties "spring.data.mongodb.auto-index-creation=true"
    @NonNull                //Specific to mongodb
    private String username;

    private String email;

    private boolean sentimentAnalysis;

    @NonNull
    private String password;

    @DBRef                  //Specific to mongodb
    private List<Journal> journalEntries = new ArrayList<>();

    @NonNull
    private List<String> roles = new ArrayList<>();
}