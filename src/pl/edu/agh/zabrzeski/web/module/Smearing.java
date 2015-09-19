package pl.edu.agh.zabrzeski.web.module;

import pl.edu.agh.zabrzeski.arduino.ArduinoTasker;
import pl.edu.agh.zabrzeski.arduino.SerialWriter;

/**
 * Created by Kamil on 2015-08-10.
 */
public final class Smearing implements ArduinoTasker  {
    private static Smearing instance;
    private int down;
    private int up;
    private final int arduinoActionDown=6;
    private final int arduinoActionUp=5;

    private  Smearing() {
        setDown(0);
        setUp(0);
    }
    
    public static Smearing getInstance() {

        if (instance == null) {
            instance = new Smearing();
        }
        return instance;
    }

    public int getDown() {
        return down;
    }

    public void setDown(int down) {
        this.down = down;
    }

    public int getUp() {
        return up;
    }

    public void setUp(int up) {
        this.up = up;
    }

    public void sendArduinoTask(int task) {
        SerialWriter.getInstance().send(task);
    }

    public void compareSessionState(int remoteStateUp, int remoteStateDown ) {
        if (remoteStateUp != up) {
            setUp(remoteStateUp);
            sendArduinoTask(arduinoActionUp);
        }

        if (remoteStateDown != down) {
            setDown(remoteStateDown);
            sendArduinoTask(arduinoActionDown);
        }
    }

    public void compareSessionState(int remoteState) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
