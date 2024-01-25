import java.sql.Date;
public class donative implements java.io.Serializable {
    private String Donatorname;
    private int ArtistID;
    private int value;
    private int DonativeID;
    private Date donation_date;

    public donative(){
        super();
    }

    //Setters

    public void setDonatorname(String donatorname) {
        Donatorname = donatorname;
    }
    public void setDate(Date date) {
        donation_date = date;
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
    public Date getDate() {
        return donation_date;
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

    public Date getDonation_date() {
        return donation_date;
    }

    public void setDonation_date(Date donation_date) {
        this.donation_date = donation_date;
    }

    
    

}