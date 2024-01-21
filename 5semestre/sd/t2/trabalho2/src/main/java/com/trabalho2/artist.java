package com.trabalho2;
public class artist implements java.io.Serializable{
    private String Name;
    private String TypeArt;
    private double latitude;
    private double longitude;
    private Boolean Performing;
    private Boolean Approved;
    private int artistID;

    public artist(){
        super();
    }

    //Setters

    public void setName(String name) {
        Name = name;
    }

    public void setTypeArt(String typeArt) {
        TypeArt = typeArt;
    }


    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setPerforming(Boolean performing) {
        Performing = performing;
    }

    public void setApproved(Boolean approved) {
        Approved = approved;
    }
    public void setArtistID(int ArtistID) {
        artistID=ArtistID;
    }


    //Getters

    public String getName() {
        return Name;
    }
    public double getLongitude() {
        return longitude;
    }
    
    public double getLatitude() {
        return latitude;
    }
    public String getTypeArt() {
        return TypeArt;
    }

    public Boolean getPerforming() {
        return Performing;
    }

    public Boolean getApproved() {
        return Approved;
    } 
    public int getArtistID() {
        return artistID;
    }       
    

}
