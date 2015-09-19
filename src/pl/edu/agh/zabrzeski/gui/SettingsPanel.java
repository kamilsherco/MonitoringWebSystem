package pl.edu.agh.zabrzeski.gui;

import gnu.io.CommPortIdentifier;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;



import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import pl.edu.agh.zabrzeski.prefs.Preferences;

/**
 * Created by Kamil on 2015-07-23.
 */
public class SettingsPanel extends JPanel{

    private FormLayout layout;
    private JTextField serverPort;
    private JComboBox<String> arduinoPort;
    private JButton okButton;
    private JTextField serverUrl;

    public SettingsPanel(){
        super();
        createLayout();
    }

    private void createLayout(){
        serverPort = new JTextField();
       // serverPort.setText(Integer.toString(Preferences.getServerPort()));

        Vector<String> ports = new Vector<String>();
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
        while (portEnum.hasMoreElements()) {
            CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
            ports.add(currPortId.getName());

        }
        arduinoPort = new JComboBox<String>(ports);
        arduinoPort.setSelectedItem(Preferences.getArduinoPort());

        serverUrl = new JTextField();
        serverUrl.setText(Preferences.getServerUrl());

        okButton = new JButton("Ok");
        okButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                String arduinoPort = getArduinoPort();
                String serverPort = getServerPort();

                String serverUrl = getServerUrl();

                if (arduinoPort != null && arduinoPort.length() != 0) {
                           Preferences.setArduinoPort(arduinoPort);
                }


                if (serverUrl.length() != 0) {
                    try {
                        Preferences.setServerUrl(serverUrl);
                    } catch (NumberFormatException e) {
                        System.out.println("invalid url");
                    }
                }
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(SettingsPanel.this);
                frame.dispose();
            }
        });

        setBorder(BorderFactory.createTitledBorder("Settings"));
        layout = new FormLayout("20px, 80dlu, fill:150dlu:grow, 20px",
                "20px, default, 10px, default, 10px, default, fill:10px:grow, default, 10px");
        setLayout(layout);
        CellConstraints c = new CellConstraints();
        add(new JLabel("Arduino port:"), c.xy(2, 2, CellConstraints.FILL, CellConstraints.CENTER));
        add(arduinoPort, c.xy(3, 2, CellConstraints.FILL, CellConstraints.CENTER));
        add(new JLabel("Server url: "), c.xy(2, 6, CellConstraints.FILL, CellConstraints.CENTER));
        add(serverUrl, c.xy(3, 6, CellConstraints.FILL, CellConstraints.CENTER));
        add(okButton, c.xy(3, 8, CellConstraints.RIGHT, CellConstraints.CENTER));
    }

    public String getArduinoPort(){
        return (String)arduinoPort.getSelectedItem();
    }

    public String getServerPort(){
        return serverPort.getText();
    }

    public String getServerUrl(){
        return serverUrl.getText();
    }

}

