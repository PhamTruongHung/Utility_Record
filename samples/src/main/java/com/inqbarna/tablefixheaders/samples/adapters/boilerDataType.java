package com.inqbarna.tablefixheaders.samples.adapters;

public class boilerDataType {
    private int id;
    private String name;
    private String checkTime;
    private Double pressureSteam;

    public boilerDataType(int id, String name, String checkTime, Double pressureSteam) {
        this.id = id;
        this.name = name;
        this.checkTime = checkTime;
        this.pressureSteam = pressureSteam;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public Double getPressureSteam() {
        return pressureSteam;
    }

    public void setPressureSteam(Double pressureSteam) {
        this.pressureSteam = pressureSteam;
    }
}
