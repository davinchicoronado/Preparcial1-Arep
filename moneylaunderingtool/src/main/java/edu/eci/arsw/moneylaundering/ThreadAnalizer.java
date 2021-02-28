/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.moneylaundering;

import java.io.File;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author David Coronado
 */
public class ThreadAnalizer extends Thread{
    
    private int x;
    private int x2;
    private TransactionAnalyzer transactionAnalyzer;
    private TransactionReader transactionReader;
    List<File> transactionFiles;
    private AtomicInteger amountOfFilesProcessed;
    private MonitorController controlador;
    
    public ThreadAnalizer(int x, int x2,TransactionAnalyzer transactionAnalyzer,TransactionReader transactionReader,AtomicInteger amountOfFilesProcessed,MonitorController controlador ){
        this.x=x;
        this.x2=x2;
        this.transactionReader=transactionReader;
        this.transactionAnalyzer=transactionAnalyzer;
        this.amountOfFilesProcessed=amountOfFilesProcessed;
        this.controlador=controlador;
    
    }
    @Override
    public void run(){
        for(int i=x;i<=x2;i++){
            List<Transaction> transactions = transactionReader.readTransactionsFromFile(transactionFiles.get(i));
            for(Transaction transaction:transactions){
                controlador.checkPause();
                transactionAnalyzer.addTransaction(transaction);
            }
            amountOfFilesProcessed.incrementAndGet();
        }
    }

    
    
    public int getX() {
        return x;
    }

    public int getX2() {
        return x2;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public void setTransactionFiles(List<File> transactionFiles) {
        this.transactionFiles = transactionFiles;
    }
    
    
}
