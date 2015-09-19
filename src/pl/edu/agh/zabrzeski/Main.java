package pl.edu.agh.zabrzeski;

import pl.edu.agh.zabrzeski.gui.MainFrame;
import java.awt.*;
/**
 * Created by Kamil on 2015-07-23.
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainFrame frame = new MainFrame();

            }
        });
    }

}
