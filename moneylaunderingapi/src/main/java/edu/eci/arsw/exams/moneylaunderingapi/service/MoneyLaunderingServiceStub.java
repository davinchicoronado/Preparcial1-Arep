package edu.eci.arsw.exams.moneylaunderingapi.service;

import edu.eci.arsw.exams.moneylaunderingapi.model.SuspectAccount;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("MoneyLaunderingServiceStub")
public class MoneyLaunderingServiceStub implements MoneyLaunderingService {
    
    
    private final Map<String,SuspectAccount> persistenceSuspectAccounts=new HashMap<>();
    
    
    @Override
    public void saveAccountStatus(SuspectAccount suspectAccount) throws MoneyLaunderingException {
        if(persistenceSuspectAccounts.containsKey(suspectAccount.getAccountId())){
            throw new MoneyLaunderingException("The given suspect account already exists:"+suspectAccount);
        }
        persistenceSuspectAccounts.put(suspectAccount.getAccountId(), suspectAccount);
    }

    @Override
    public SuspectAccount getAccountStatus(String accountId) throws MoneyLaunderingException {
        if(!persistenceSuspectAccounts.containsKey(accountId)){
            throw new MoneyLaunderingException("No exist:"+accountId);
        }
        SuspectAccount sAccount= persistenceSuspectAccounts.get(accountId);  
        return sAccount;
    }

    @Override
    public List<SuspectAccount> getSuspectAccounts() throws MoneyLaunderingException {
        if(persistenceSuspectAccounts.isEmpty()){
            throw new MoneyLaunderingException("There are not any suspect account");
        }
        List<SuspectAccount> suspectAcounts = new ArrayList();
          
        for(Map.Entry<String,SuspectAccount>  entry : persistenceSuspectAccounts.entrySet()){
            suspectAcounts.add(entry.getValue());
        }
      
        return suspectAcounts;
    }

    @Override
    public void addAmountOfSmallTransactions(String accountId) throws MoneyLaunderingException {
        if(!persistenceSuspectAccounts.containsKey(accountId)){
            throw new MoneyLaunderingException("No exist:"+accountId);
        }    
        SuspectAccount sAccount= persistenceSuspectAccounts.get(accountId);
        sAccount.setAmountOfSmallTransactions(sAccount.getAmountOfSmallTransactions()+1);
    }
    
    
}
