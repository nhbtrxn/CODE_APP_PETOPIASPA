package com.nhom9.models;

public class Appointment {
    String time;
    String servicetype;
    String petname;

    //Constructor


    public Appointment(String time, String servicetype, String petname) {
        this.time = time;
        this.servicetype = servicetype;
        this.petname = petname;
    }

    //Getter and setter

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getServicetype() {
        return servicetype;
    }

    public void setServicetype(String servicetype) {
        this.servicetype = servicetype;
    }

    public String getPetname() {
        return petname;
    }

    public void setPetname(String petname) {
        this.petname = petname;
    }
}
