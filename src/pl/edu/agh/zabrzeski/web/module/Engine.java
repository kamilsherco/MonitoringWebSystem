package pl.edu.agh.zabrzeski.web.module;

import pl.edu.agh.zabrzeski.arduino.ArduinoTasker;
import pl.edu.agh.zabrzeski.arduino.SerialWriter;


/**
 * Created by Kamil on 2015-08-10.
 */
public final class Engine implements ArduinoTasker {
    private static Engine instance;
    private int power;
    private final int arduinoActionOn=3;
    private final int arduinoActionOff=4;

    private Engine() {
        setPower(0);
        sendArduinoTask(arduinoActionOff);
    }

  public static Engine getInstance(){

        if(instance == null){
            instance = new Engine();
        }
        return instance;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }
    
    public void sendArduinoTask(int task)
    {
        SerialWriter.getInstance().send(task);
    }

    public void compareSessionState(int remoteState) {
        if (remoteState != power) {
            setPower(remoteState);
            if (power == 1) {
                sendArduinoTask(arduinoActionOn);
            } else {
                sendArduinoTask(arduinoActionOff);
            }
        }
    }
}
