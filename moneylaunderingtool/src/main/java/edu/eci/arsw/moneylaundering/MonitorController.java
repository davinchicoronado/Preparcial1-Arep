/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.moneylaundering;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author David Coronado
 */
public class MonitorController {
    
    private boolean pause;
    public MonitorController(){
        pause=false;
    }
    
    public synchronized void checkPause(){
        if(pause){
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(MonitorController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    
    }
    
    public synchronized void setPause(){
        if(pause){
            pause=false;
            notifyAll();
        }
        else{
            pause=true;
        }
    
    }
}
