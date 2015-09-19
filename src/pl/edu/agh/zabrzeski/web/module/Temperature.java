package pl.edu.agh.zabrzeski.web.module;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import pl.edu.agh.zabrzeski.arduino.SerialWriter;
import pl.edu.agh.zabrzeski.prefs.Preferences;

/**
 * Created by Kamil on 2015-08-10.
 */
public final class Temperature {

    private static Temperature instance;
    private double value;

    private Temperature() {
        setValue(getTemperatureFromArduino());
    }

    public static Temperature getInstance() {

        if (instance == null) {
            instance = new Temperature();
        }
        return instance;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getTemperatureFromArduino() {
        return SerialWriter.getInstance().getTemperatuer();
    }

    public void sendTemperatureToRemoteServer(String token) throws Exception {
        getTemperatureFromArduino();
        String USER_AGENT = "Mozilla/5.0";
        String _apiCereateSesionUrl = Preferences.getServerUrl() + "api/update_temperature.php";
        URL obj = new URL(_apiCereateSesionUrl);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "token=" + token + "&" + "temperature=" + value;

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + _apiCereateSesionUrl);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

    }

}
