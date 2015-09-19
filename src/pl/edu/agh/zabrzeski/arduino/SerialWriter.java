package pl.edu.agh.zabrzeski.arduino;

/**
  * Class that is responsible for communication with Arduino. Supports write and read.
  * Only one instance can be created.
 * Created by Kamil on 2015-08-06.
 */


import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import pl.edu.agh.zabrzeski.prefs.Preferences;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;


public class SerialWriter implements SerialPortEventListener{
    private static SerialWriter instance;
    private SerialPort serialPort;
    /**
     * Listeners, notify when message from Arduino arrives
     */
    private double temperatureListener;
    private OutputStream output;
    private InputStream input;
    private static final int TIME_OUT = 2000;
    /**
     * Bits per second for COM port.
     * */
    private static final int DATA_RATE = 9600;

    private SerialWriter(){
       
    }

    /**
     * Returns single instance of SerialWriter.
     * @return	instance of SerialWriter
     */
    public static SerialWriter getInstance(){
        if(instance == null){
            instance = new SerialWriter();
        }
        return instance;
    }

    public void start() throws Exception{
        CommPortIdentifier portId = null;
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

        //First, Find an instance of serial port as set in PORT_NAMES.
        while (portEnum.hasMoreElements()) {
            CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
            if (currPortId.getName().equals(Preferences.getArduinoPort())) {
                portId = currPortId;
            }
        }
        if (portId == null) {
            throw(new Exception("Could not find COM port."));
        }

        try {
            // open serial port
            serialPort = (SerialPort) portId.open(this.getClass().getName(),
                    TIME_OUT);

            // set port parameters
            serialPort.setSerialPortParams(DATA_RATE,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);

            //register listener
            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);
            output = serialPort.getOutputStream();
            input = serialPort.getInputStream();
        } catch (Exception e) {
            throw(new Exception("Could not open COM port."));
        }
    }

    /**
     * Sends data to Arduino
     * @param message message to be send
     */
    synchronized public void send(int message){
        try {
            output.write((byte)message);
            output.flush();
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    /**
     * Closing serial port. It should be close when program is closing. After that communication cannot be done.
     */
    public synchronized void close() {
        if (serialPort != null) {
            serialPort.removeEventListener();
            serialPort.close();
        }
 
        instance = null;
    }

    /**
     * Register listener to notify when new message arrives
     * @param listener	object implementing ArduinoEventListener interface
     */
 public double getTemperatuer(){
     return temperatureListener;
 }

    @Override
    public void serialEvent(SerialPortEvent event) {
        System.out.println("serialEvent");
        String result = null;
        switch (event.getEventType()) {
            case SerialPortEvent.DATA_AVAILABLE:
                // we get here if data has been received
                byte[] readBuffer = new byte[100];
                try {
                    while (input.available() > 0) {
                        input.read(readBuffer);
                    }
                    result  = new String(readBuffer);
                    System.out.println(result);
                } catch (IOException e) {
                }
                break;
            default:
                break;
        }
        if(result != null){
            temperatureListener=Double.parseDouble(result);
            }

    }
   
}