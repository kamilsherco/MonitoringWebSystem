/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.agh.zabrzeski.arduino;

/**
 *
 * @author kzabrzeski
 */
public interface ArduinoTasker {
    
    public void sendArduinoTask(int task);
    public void compareSessionState(int remoteState);
    
}
