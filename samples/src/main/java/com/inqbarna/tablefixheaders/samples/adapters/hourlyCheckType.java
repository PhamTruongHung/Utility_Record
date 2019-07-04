package com.inqbarna.tablefixheaders.samples.adapters;

public class hourlyCheckType {
    private int id;
    private String dateOfCheck;
    private String timeOfCheck;
    private String location;
    private String personCheck;

    public hourlyCheckType() {
    }

    public hourlyCheckType(int id, String dateOfCheck, String timeOfCheck, String location, String personCheck) {
        this.id = id;
        this.dateOfCheck = dateOfCheck;
        this.timeOfCheck = timeOfCheck;
        this.location = location;
        this.personCheck = personCheck;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateOfCheck() {
        return dateOfCheck;
    }

    public void setDateOfCheck(String dateOfCheck) {
        this.dateOfCheck = dateOfCheck;
    }

    public String getTimeOfCheck() {
        return timeOfCheck;
    }

    public void setTimeOfCheck(String timeOfCheck) {
        this.timeOfCheck = timeOfCheck;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPersonCheck() {
        return personCheck;
    }

    public void setPersonCheck(String personCheck) {
        this.personCheck = personCheck;
    }
}
