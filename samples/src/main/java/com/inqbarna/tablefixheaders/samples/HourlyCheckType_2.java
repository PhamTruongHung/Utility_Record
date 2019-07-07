package com.inqbarna.tablefixheaders.samples;

public class HourlyCheckType_2 {
    private String timeCheck;
    private String personCheck;

    public HourlyCheckType_2() {
    }

    public HourlyCheckType_2(String timeCheck, String personCheck) {
        this.timeCheck = timeCheck;
        this.personCheck = personCheck;
    }

    public String getTimeCheck() {
        return timeCheck;
    }

    public void setTimeCheck(String timeCheck) {
        this.timeCheck = timeCheck;
    }

    public String getPersonCheck() {
        return personCheck;
    }

    public void setPersonCheck(String personCheck) {
        this.personCheck = personCheck;
    }
}
