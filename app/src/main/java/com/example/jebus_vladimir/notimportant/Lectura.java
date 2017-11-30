package com.example.jebus_vladimir.notimportant;

/**
 * Created by MSI 0ND on 11/30/2017.
 */
public class Lectura {
    private String date;
    private String time;
    private String sys;
    private String dis;

    public Lectura(String date, String time, String sys, String dis) {
        this.date = date;
        this.time = time;
        this.sys = sys;
        this.dis = dis;
    }

    public String getDate() {
        return date;
    }
    public String getTime() {
        return time;
    }
    public String getDis() {
        return dis;
    }
    public String getSys() {
        return sys;
    }
}
