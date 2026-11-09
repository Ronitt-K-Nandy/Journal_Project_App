package com.newspaper.journal.repositories.impl;

import com.newspaper.journal.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;


public class UserRepositoryImpl {

    @Autowired
    private MongoTemplate mongoTemplate;
    // MongoTemplate is equivalent of JdbcTemplate

    /*
    Key Differences

    1. CRUD Operations

    MongoRepository: Excellent support for standard CRUD operations.
    MongoTemplate: Supports CRUD operations with greater flexibility.

    2. Custom Queries

    MongoRepository: Supports query methods and @Query annotations.
    MongoTemplate: Allows fully customizable queries.

    3. Dynamic Queries

    MongoRepository: Limited support; Specifications are not directly equivalent.
    MongoTemplate: Excellent support for building dynamic queries.

    4. Complex Aggregations

    MongoRepository: Supports aggregations using the @Aggregation annotation.
    MongoTemplate: Provides excellent support for complex aggregation pipelines.

    5. Pagination

    MongoRepository: Provides built-in pagination support.
    MongoTemplate: Supports pagination through query configuration.

    6. Bulk Operations

    MongoRepository: Bulk operations are less direct.
    MongoTemplate: Provides excellent support for bulk operations.

    7. Boilerplate Code

    MongoRepository: Requires very little boilerplate code.
    MongoTemplate: Generally requires more code.

    8. Query Control

    MongoRepository: Offers less control over query execution.
    MongoTemplate: Provides greater control over query construction and execution.
*/
    public List<UserEntity> getUsersForSentimentAnalysis(){
        Query query = new Query();
        query.addCriteria(Criteria.where("email").exists(true).ne("").ne(null)
                .regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"));
        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));

        /*Criteria criteria = new Criteria();
        query.addCriteria(criteria.orOperator(Criteria.where("email").exists(true),
                Criteria.where("sentimentAnalysis").is(true)));*/


        List<UserEntity> users = mongoTemplate.find(query, UserEntity.class);
        return users;
    }
}