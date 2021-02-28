package edu.eci.arsw.exams.moneylaunderingapi.service;

import edu.eci.arsw.exams.moneylaunderingapi.model.SuspectAccount;

import java.util.List;

public interface MoneyLaunderingService {
    
    public void saveAccountStatus(SuspectAccount suspectAccount) throws MoneyLaunderingException;
    
    public SuspectAccount getAccountStatus(String accountId) throws MoneyLaunderingException;
    
    public List<SuspectAccount> getSuspectAccounts() throws MoneyLaunderingException;
    
    public void addAmountOfSmallTransactions(String accountId) throws MoneyLaunderingException;
}
