package com.newspaper.journal.services;

import com.newspaper.journal.entities.IndianHistoryExternal;
import com.newspaper.journal.entities.Journal;
import com.newspaper.journal.entities.UserEntity;
import com.newspaper.journal.repositories.JournalRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class IndianHistoryExternalService {
 //check the website from google from : https://newsdata.io/search-news

    private static final String API_KEY="pub_a528af09587747fd90a5f6dd53820ecf";
    private static final String API="https://newsdata.io/api/1/latest?apikey=API_KEY&q=PARAM_VAR";

    @Autowired
    private RestTemplate restTemplate;
    public IndianHistoryExternal getHistory(String param)
    {
        String finalAPI= API.replace("API_KEY",API_KEY);
        finalAPI = finalAPI.replace("PARAM_VAR",param);
        //Serialization = POJO object ->JSON
        //Deserailization = JSON -> POJO object , here we are doing deserialization
        ResponseEntity<IndianHistoryExternal> response = restTemplate.exchange(finalAPI,
                                                                                HttpMethod.GET,
                                                                                null,
                                                                                IndianHistoryExternal.class);
        IndianHistoryExternal historyBody= response.getBody();
        return historyBody;
    }

    /*public String getHistory(String param)
    {
        String finalAPI= API.replace("API_KEY",API_KEY);
        finalAPI = finalAPI.replace("PARAM_VAR",param);
        //Serialization = POJO object ->JSON
        //Deserailization = JSON -> POJO object , here we are doing deserialization
        ResponseEntity<String> response = restTemplate.exchange(finalAPI,
                                                                                HttpMethod.GET,
                                                                                null,
                                                                                String.class);

        return response.toString();
    }*/

}
