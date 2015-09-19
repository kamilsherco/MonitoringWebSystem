package pl.edu.agh.zabrzeski.gui;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
/**
 * Created by Kamil on 2015-07-23.
 */

abstract public class RowComponent {
    public enum COMPONENET_STATE{
        ON, OFF, LOADING;
    }
    private JLabel name;
    private JLabel token;
    private JButton button;
    private JLabel state;
    private COMPONENET_STATE mState;

    public RowComponent(String name){
        button = new JButton();
        this.name = new JLabel(name);
        this.token= new JLabel("");

        this.state = new JLabel();
        this.state.setOpaque(true);
        this.state.setHorizontalTextPosition(JLabel.CENTER);
        this.state.setVerticalTextPosition(JLabel.CENTER);

        setState(COMPONENET_STATE.OFF);

        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                onClicked();
            }
        });

    }

    public void setState(COMPONENET_STATE state){
        this.mState = state;
        switch(this.mState){
            case ON:
                this.state.setBackground(Color.GREEN);
                this.state.setText("Running");
                button.setText("Stop");
                break;
            case LOADING:
                this.state.setBackground(Color.YELLOW);
                this.state.setText("Starting");
                button.setText("Stop");
                break;
            case OFF:
                this.state.setBackground(Color.RED);
                this.state.setText("Stopped");
                button.setText("Start");
                break;
        }
    }

    public COMPONENET_STATE toggle(){
        switch (mState) {
            case ON:
                mState = COMPONENET_STATE.OFF;
                break;
            default:
                mState = COMPONENET_STATE.ON;
                break;
        }
        return mState;
    }

    public COMPONENET_STATE getState(){
        return mState;
    }

    public JLabel getNameView(){
        return name;
    }

    public JButton getButtonView(){
        return button;
    }

    public JLabel getStateView(){
        return state;
    }

    public JLabel getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token.setText(token);
        this.token.setBackground(Color.GREEN);
    }

    abstract protected void onClicked();
}
