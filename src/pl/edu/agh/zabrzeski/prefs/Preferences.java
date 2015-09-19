package pl.edu.agh.zabrzeski.prefs;

/**
 * Created by Kamil on 2015-08-02.
 */
public class Preferences {
    private static final String ARDUINO_PORT = "arduino_port";
    private static final String SERVER_URL = "server_url";



    private java.util.prefs.Preferences prefs;

    private Preferences(){
        prefs = java.util.prefs.Preferences.userNodeForPackage(getClass());
    }

    private static java.util.prefs.Preferences getPrefs(){
        return new Preferences().prefs;
    }

    /**
     * Returns port that Arduino is attached to
     * @return 	port number
     */
    public static String getArduinoPort(){
        return getPrefs().get(ARDUINO_PORT, "COM1");
    }

    /**
     * Puts port that Arduino is attached to
     * @param port	port number
     */
    public static void setArduinoPort(String port){
        getPrefs().put(ARDUINO_PORT, port);
    }

    public static String getServerUrl() {
        return getPrefs().get(SERVER_URL, "http://example.com/");
    }

    public static void setServerUrl(String serverURL) {
        getPrefs().put( SERVER_URL,serverURL);
    }


}