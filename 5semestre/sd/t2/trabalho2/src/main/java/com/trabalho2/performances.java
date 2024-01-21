package com.trabalho2;
import java.sql.Date;

public class performances implements java.io.Serializable{
    private int actuation_id;
    private int artist_id;
    private double latitude;
    private double longitude;
    private Date performance_date;

    public int getArtist_id() {
        return artist_id;
    }
    public int getActuation_id() {
        return actuation_id;
    }

    public void setArtist_id(int artist_id) {
        this.artist_id = artist_id;
    }
    public void setActuation_id(int actuation_id) {
        this.actuation_id = actuation_id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Date getperformance_date() {
        return performance_date;
    }

    public void setperformance_date(Date performance_date) {
        this.performance_date = performance_date;
    }

    public performances() {
        super();
    }

}