package com.newspaper.journal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@Configuration
public class TransactionConfig {
    /*
		When we apply @Transactional then this error is thrown.

		"org.springframework.dao.InvalidDataAccessApiUsageException:
		Command execution failed on MongoDB server with error 20 (IllegalOperation):
		'Transaction numbers are only allowed on a replica set member or mongos' on server localhost:27017.
		The full response is {"ok": 0.0, "errmsg": "Transaction numbers are only allowed on
		a replica set member or mongos", "code": 20, "codeName": "IllegalOperation"}"


		The error "Transaction numbers are only allowed on a replica set member or mongos" occurs
		because MongoDB transactions require the database to run as a replica set or a sharded cluster,
		not a standalone instance. The standalone MongoDB instance you are connecting to (localhost:27017)
		does not support transactions.

	*/
    @Bean
    public PlatformTransactionManager onlyCheckForTransaction(MongoDatabaseFactory dbFactory)
    {
        return new MongoTransactionManager(dbFactory);
    }
}
