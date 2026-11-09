package com.newspaper.journal.controllers;

import com.newspaper.journal.entities.IndianHistoryExternal;
import com.newspaper.journal.entities.Journal;
import com.newspaper.journal.entities.Result;
import com.newspaper.journal.entities.UserEntity;
import com.newspaper.journal.repositories.JournalRepository;
import com.newspaper.journal.services.IndianHistoryExternalService;
import com.newspaper.journal.services.JournalService;
import com.newspaper.journal.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;


@RestController
@RequestMapping("/api")
public class APIController {
@Autowired
private IndianHistoryExternalService indianHistoryExternalService;
    @GetMapping("/history")
    public ResponseEntity<?> newsHistory(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        IndianHistoryExternal indianHistoryResponse = indianHistoryExternalService.getHistory("Indian History");
        //String indianHistoryResponse = indianHistoryExternalService.getHistory("Indian");
        Result fetchedIndianHistory = null;
        if(indianHistoryResponse!=null)
        {
            ArrayList<Result> indianHistoryResults = indianHistoryResponse.getResults();
            fetchedIndianHistory = indianHistoryResults.get(new Random().nextInt(indianHistoryResults.size() - 1));
        }
        return new ResponseEntity<>("Hi "+ authentication.getName() + " Bharat's history is about -> "+
                fetchedIndianHistory.getTitle()+" : "+fetchedIndianHistory.getDescription(), HttpStatus.OK);
    }
}