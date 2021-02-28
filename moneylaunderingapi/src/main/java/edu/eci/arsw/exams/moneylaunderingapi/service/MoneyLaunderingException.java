/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.exams.moneylaunderingapi.service;

/**
 *
 * @author lenovo
 */
public class MoneyLaunderingException extends Exception {
    
    public MoneyLaunderingException(String message){
        super(message);
    }
    public MoneyLaunderingException(String message, Throwable cause) {
        super(message,cause);
    
    }
}
