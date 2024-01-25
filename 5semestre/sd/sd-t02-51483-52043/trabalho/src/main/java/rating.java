public class rating implements java.io.Serializable{
    
    private int artist_id;
    private String username ;
    private int rating;



    public int getArtist_id() {
        return artist_id;
    }



    public void setArtist_id(int artist_id) {
        this.artist_id = artist_id;
    }



    public String getUsername() {
        return username;
    }



    public void setUsername(String username) {
        this.username = username;
    }



    public int getRating() {
        return rating;
    }



    public void setRating(int rating) {
        this.rating = rating;
    }



    public rating( int artist_id, String username, int rating) {
        this.artist_id = artist_id;
        this.username = username;
        this.rating = rating;
    } 

    

}
