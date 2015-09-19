package pl.edu.agh.zabrzeski.gui;
import java.awt.Dimension;

import javax.swing.JFrame;
/**
 * Created by Kamil on 2015-07-23.
 */
public class SettingsFrame extends JFrame{
    private SettingsPanel setttings;

    public SettingsFrame(){
        super("Settings");
        setLocationByPlatform(true);
        setVisible(true);
        setttings = new SettingsPanel();
        setContentPane(setttings);
        pack();
        setMinimumSize(new Dimension(500, 210));
    }
}
