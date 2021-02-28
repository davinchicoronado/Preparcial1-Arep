package edu.eci.arsw.exams.moneylaunderingapi;


import edu.eci.arsw.exams.moneylaunderingapi.model.SuspectAccount;
import edu.eci.arsw.exams.moneylaunderingapi.service.MoneyLaunderingException;
import edu.eci.arsw.exams.moneylaunderingapi.service.MoneyLaunderingService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@RequestMapping( value = "/fraud-bank-accounts")
public class MoneyLaunderingController
{   
    @Autowired
    @Qualifier("MoneyLaunderingServiceStub")
    MoneyLaunderingService moneyLaunderingService;

    @RequestMapping(method = RequestMethod.GET)
    public  ResponseEntity<?> getSuspectAccounts() {
        List<SuspectAccount> accounts;
        try {
            accounts = moneyLaunderingService.getSuspectAccounts();
            return new ResponseEntity<>(accounts,HttpStatus.ACCEPTED);
        } catch (MoneyLaunderingException ex) {
            Logger.getLogger(MoneyLaunderingController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Http Error Code: 404. Resource not found",HttpStatus.NOT_FOUND);
        }
        
        
    }
    @RequestMapping(method = RequestMethod.POST)
    public  ResponseEntity<?> saveSuspectAccount(@RequestBody SuspectAccount account) {
        try {
            moneyLaunderingService.saveAccountStatus(account);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (MoneyLaunderingException ex) {
            Logger.getLogger(MoneyLaunderingController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Http Error Code: 400. Bad Request",HttpStatus.BAD_REQUEST);
        }
        
    }
    @RequestMapping(value="/{accountId}", method = RequestMethod.GET)
    public  ResponseEntity<?> getSuspectAccount(@PathVariable("accountId") String accountId) {
    
        try {
            SuspectAccount sAccount= moneyLaunderingService.getAccountStatus(accountId);
            return new ResponseEntity<>(sAccount,HttpStatus.ACCEPTED);
            
        } catch (MoneyLaunderingException ex) {
            Logger.getLogger(MoneyLaunderingController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Http Error Code: 404. Resource not found",HttpStatus.NOT_FOUND);
        }

    }
    
    @RequestMapping(value="/{accountId}", method = RequestMethod.PUT)
    public  ResponseEntity<?> addAmountOfSmallTransactions(@PathVariable("accountId") String accountId) {
        
        try {
            moneyLaunderingService.addAmountOfSmallTransactions(accountId);
            return new ResponseEntity<>(HttpStatus.ACCEPTED); 
        } catch (MoneyLaunderingException ex) {
            Logger.getLogger(MoneyLaunderingController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Http Error Code: 400. Bad Request",HttpStatus.BAD_REQUEST);
        }
    
    
    }
    
    
    
    
    

    
}
