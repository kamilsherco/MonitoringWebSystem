package pl.edu.agh.zabrzeski.gui;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import pl.edu.agh.zabrzeski.web.MonitoringSession;

import javax.swing.*;
import pl.edu.agh.zabrzeski.arduino.SerialWriter;

/**
 * Created by Kamil on 2015-07-23.
 */
public class MainPanel extends JPanel implements ErrorCallback {

    public static ErrorCallback messageCallback;

    //layouts
    private FormLayout layout;
    private RowComponent componentServer;
    private RowComponent componentArduino;
    private RowComponent componentToken;
    private MonitoringSession session;

    public MainPanel(ErrorCallback callback) {
        super();
        createComponents();
        createLayout();

        MainPanel.messageCallback = callback;
    }

    private void createComponents() {
        componentArduino = new RowComponent("Arduino") {

            @Override
            protected void onClicked() {
				Thread th = new Thread(new Runnable() {
					
					@Override
					public void run() {
                    switch (getState()) {
                        case OFF:
                            
                            setState(COMPONENET_STATE.LOADING);
                            try {
                                SerialWriter.getInstance().start();
                                setState(COMPONENET_STATE.ON);
                            } catch (Exception e) {
                                messageCallback.postMessage("Arduino", e.getMessage());
                                setState(COMPONENET_STATE.OFF);
                            }
                            break;
                        case ON:
                            setState(COMPONENET_STATE.OFF);
                            SerialWriter.getInstance().close();
                            break;
                        case LOADING:
                            setState(COMPONENET_STATE.OFF);
                            SerialWriter.getInstance().close();
                            break;
                            
                    }
					}});
				
                th.start();
            }
        };

        componentServer = new RowComponent("Server") {

            @Override
            protected void onClicked() {
				Thread th = new Thread(new Runnable() {
					
					@Override
					public void run() {
                    switch (getState()) {
                        case OFF:
                            
                            setState(COMPONENET_STATE.ON);
                            MonitoringSession.getInstance();
                            MonitoringSession.getInstance().setIsRuning(true);
                            
                            componentToken.setToken(MonitoringSession.getInstance().getToken());
                            
                            MonitoringSession.getInstance().getSessionDetail(MonitoringSession.getInstance().getToken());
                            
                            break;
                        case ON:
                            setState(COMPONENET_STATE.OFF);
                            MonitoringSession.getInstance().setIsRuning(false);
                            break;
                            
                    }
                }});
                th.start();
            }
        };

        componentToken = new RowComponent("Session name") {
            @Override
            protected void onClicked() {

            }
        };

    }

    private void createLayout() {
        setBorder(BorderFactory.createTitledBorder("Services"));
        layout = new FormLayout("fill:20px:grow, default, fill:20px:grow, 80px, fill:20px:grow, default, fill:20px:grow",
                "fill:10px:grow, default, fill:10px:grow, default, fill:10px:grow, default, fill:10px:grow");
        setLayout(layout);
        addComponentToLayout(componentArduino, 2);
        addComponentToLayout(componentServer, 4);
        //   addComponentToLayout(componentCamera, 6);
        addTokenComponentToLayout(componentToken, 6);

    }

    private void addComponentToLayout(RowComponent component, int position) {
        CellConstraints c = new CellConstraints();
        add(component.getNameView(), c.xy(2, position, CellConstraints.FILL, CellConstraints.CENTER));
        add(component.getStateView(), c.xy(4, position, CellConstraints.FILL, CellConstraints.CENTER));
        add(component.getButtonView(), c.xy(6, position, CellConstraints.FILL, CellConstraints.CENTER));
    }

    private void addTokenComponentToLayout(RowComponent component, int position) {
        CellConstraints c = new CellConstraints();
        add(component.getNameView(), c.xy(2, position, CellConstraints.FILL, CellConstraints.CENTER));
        add(component.getToken(), c.xy(4, position, CellConstraints.FILL, CellConstraints.CENTER));

    }

    public static void setMessage(String message) {

        messageCallback.postMessage("Server", message);
    }

    @Override
    public void postMessage(String component, String error) {
        componentServer.setState(RowComponent.COMPONENET_STATE.OFF);
        messageCallback.postMessage("Server", error);
    }
}
