package com.example.jebus_vladimir.notimportant;

public class MiLectura {

    private String date;
    private String time;
    private String sys;
    private String dis;

    public MiLectura(String date, String time, String sys, String dis) {
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
    public String getSys() {
        return sys;
    }
    public String getDis() {
        return dis;
    }
}