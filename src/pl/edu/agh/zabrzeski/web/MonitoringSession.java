package pl.edu.agh.zabrzeski.web;


import pl.edu.agh.zabrzeski.gui.MainPanel;
import pl.edu.agh.zabrzeski.prefs.Preferences;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import pl.edu.agh.zabrzeski.web.module.Engine;
import pl.edu.agh.zabrzeski.web.module.Heat;
import pl.edu.agh.zabrzeski.web.module.Smearing;
import pl.edu.agh.zabrzeski.web.module.Temperature;

/**
 * Created by Kamil on 2015-07-23.
 */
public final class MonitoringSession {
    private static MonitoringSession instance;
    private static String  url=Preferences.getServerUrl()+"api/get_session.php?token=";
    private String token;
    private boolean isRuning=false;

    public boolean isRuning() {
        return isRuning;
    }

    public void setIsRuning(boolean isRuning) {
        this.isRuning = isRuning;
    }

    private MonitoringSession(){
        initSession();

        try {
         createSession();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static MonitoringSession getInstance(){

        if(instance == null){
            instance = new MonitoringSession();
        }
        return instance;
    }

    public void initSession() {
       
        Engine.getInstance();
        Heat.getInstance();
        Smearing.getInstance();
        Temperature.getInstance();

        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        String tempToken = bytes.toString();
        String sessionToken = tempToken.substring(3);


        try {
            Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start chrome " + Preferences.getServerUrl() + "connection.html?" + sessionToken});
        } catch (IOException e) {
          System.out.println(e);
        }

        setToken(sessionToken);
        MainPanel.setMessage("Token: " + sessionToken);
    }


    public void getSessionDetail(String token) {

    while(isRuning) {
        try {
            Reader reader = new InputStreamReader(new URL(url + token).openStream());
            Gson gson = new GsonBuilder().create();
            DatabaseModel obj = gson.fromJson(reader, DatabaseModel.class);
            compareSessionState(obj);
            Thread.sleep(2000);
            MainPanel.setMessage("Status: "+obj.getSuccess());

        } catch (IOException e) {
            System.out.println(e);
        } catch (JsonSyntaxException e) {
            System.out.println(e);
        } catch (JsonIOException e) {
            System.out.println(e);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }

    }

    public void createSession() throws Exception {
        String USER_AGENT = "Mozilla/5.0";
        String _apiCereateSesionUrl = Preferences.getServerUrl()+"api/create_session.php";
        URL obj = new URL(_apiCereateSesionUrl);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "token="+token;

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
    
    public void compareSessionState(DatabaseModel remoteState){
        Engine.getInstance().compareSessionState(remoteState.getPower_e());
        Heat.getInstance().compareSessionState(remoteState.getPower_h());
        Smearing.getInstance().compareSessionState(remoteState.getS_up(), remoteState.getS_down());
        try {
            Temperature.getInstance().sendTemperatureToRemoteServer(token);
        } catch (Exception ex) {
            Logger.getLogger(MonitoringSession.class.getName()).log(Level.SEVERE, null, ex);
        }
  }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
