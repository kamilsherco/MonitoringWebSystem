package pl.edu.agh.zabrzeski.web.module;

import pl.edu.agh.zabrzeski.arduino.ArduinoTasker;
import pl.edu.agh.zabrzeski.arduino.SerialWriter;

/**
 * Created by Kamil on 2015-09-07.
 */
public final class Heat implements ArduinoTasker {

    private static Heat instance;
    private int power;
    private final int arduinoActionOn=1;
    private final int arduinoActionOff=2;

    private Heat() {
        setPower(0);
        sendArduinoTask(arduinoActionOff);
    }

    public static Heat getInstance() {

        if (instance == null) {
            instance = new Heat();
        }
        return instance;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public void sendArduinoTask(int task) {
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
