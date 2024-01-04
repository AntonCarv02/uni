
public class artist implements java.io.Serializable{
    private String Name;
    private String TypeArt;
    private String Location;
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

    public void setLocation(String location) {
        Location = location;
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

    public String getTypeArt() {
        return TypeArt;
    }

    public String getLocation() {
        return Location;
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
