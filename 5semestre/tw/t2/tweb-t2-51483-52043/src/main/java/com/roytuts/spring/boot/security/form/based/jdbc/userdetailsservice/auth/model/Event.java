package com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.model;
import java.util.*;
import java.text.SimpleDateFormat;

public class Event {
    private int Event_ID;
    private String name;
    private Date date;
    private String Description;
    private float Price;

    

    public Event(int event_ID, String name, Date date, String description, float price) {
        this.Event_ID = event_ID;
        this.name = name;
        this.date = date;
        this.Description = description;
        this.Price = price;
    }
    
    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
    }

    public int getEvent_ID(){
        return Event_ID;
    }

    public void setEvent_ID(int Event_ID) {
        this.Event_ID = Event_ID;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    @Override
    public String toString() {
        String formattedDate = new SimpleDateFormat("dd/MM/yyyy").format(date);
        return "Event:[" + Event_ID + ", " + name + ", " + formattedDate + ", " + Price + ", " + Description + "]";
    }

}