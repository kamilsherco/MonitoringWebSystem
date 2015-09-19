package pl.edu.agh.zabrzeski.web;

/**
 * Created by Kamil on 2015-08-03.
 */
public class DatabaseModel {

    private int success;
    private int id;
    private int power_e;
    private int power_h;
    private int s_up;
    private int s_down;
    private int active;
    private double temperature;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPower_e() {
        return power_e;
    }

    public void setPower_e(int power_e) {
        this.power_e = power_e;
    }

    public int getPower_h() {
        return power_h;
    }

    public void setPower_h(int power_h) {
        this.power_h = power_h;
    }

    public int getS_up() {
        return s_up;
    }

    public void setS_up(int s_up) {
        this.s_up = s_up;
    }

    public int getS_down() {
        return s_down;
    }

    public void setS_down(int s_down) {
        this.s_down = s_down;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}
