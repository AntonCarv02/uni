public class donative implements java.io.Serializable {
    private String Donatorname;
    private int ArtistID;
    private int value;
    private int DonativeID;

    public donative(){
        super();
    }

    //Setters

    public void setDonatorname(String donatorname) {
        Donatorname = donatorname;
    }

    public void setArtistID(int artistID) {
        ArtistID = artistID;
    }

    public void setDonativeID(int donativeID) {
        DonativeID = donativeID;
    }

    public void setValue(int value) {
        this.value = value;
    }
    
    //Getters

    public String getDonatorname() {
        return Donatorname;
    }

    public int getArtistID() {
        return ArtistID;
    }
    
    public int getDonativeID() {
        return DonativeID;
    }

    public int getValue() {
        return value;
    }

    
    

}