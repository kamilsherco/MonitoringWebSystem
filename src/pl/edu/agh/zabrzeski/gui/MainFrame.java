package pl.edu.agh.zabrzeski.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.*;

/**
 * Created by Kamil on 2015-07-23.
 */



public class MainFrame extends JFrame implements ErrorCallback{
    private static final long serialVersionUID = -8062640592907358339L;

    private JTextArea errorMessage;
    private GridLayout mainLayout;

    public MainFrame(){
        super("Web Monitor Server");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setVisible(true);
        mainLayout = new GridLayout(2, 1);
        mainLayout.setVgap(10);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        contentPane.setLayout(mainLayout);
        contentPane.add(new MainPanel(this));

        errorMessage = new JTextArea();
        JScrollPane scroll = new JScrollPane(errorMessage);
        contentPane.add(scroll);
        setContentPane(contentPane);
        createMenuBar();
        setMinimumSize(new Dimension(500, 410));
        pack();
        postMessage("Main", "Initialized");
    }

    private void createMenuBar(){

        JMenuBar menuBar = new JMenuBar();

        JMenu menu = new JMenu("Menu");
        JMenuItem itemSettings = menu.add(new JMenuItem("Settings"));
        JMenuItem itemExit = menu.add(new JMenuItem("Exit"));

        itemSettings.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new SettingsFrame();
            }
        });

        itemExit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                MainFrame.this.dispatchEvent(new WindowEvent(MainFrame.this, WindowEvent.WINDOW_CLOSING));
            }
        });


        menuBar.add(menu);
        setJMenuBar(menuBar);
    }

    @Override
    public void postMessage(String component, String error){
        StringBuilder strBuilder = new StringBuilder();
        Calendar cal = Calendar.getInstance();
        cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        strBuilder.append(sdf.format(cal.getTime()));
        strBuilder.append(" [").append(component).append("]");
        strBuilder.append("\t");
        strBuilder.append(error);
        errorMessage.setText(errorMessage.getText() + "\n" + strBuilder.toString());
    }
}

