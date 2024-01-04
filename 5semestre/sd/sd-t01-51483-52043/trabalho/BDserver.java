
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BDserver implements java.io.Serializable {

    private Connection con = null;
    private Statement stmt = null;

    private String PG_HOST;
    private String PG_DB;
    private String USER;
    private String PWD;

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public Statement getStmt() {
        return stmt;
    }

    public void setStmt(Statement stmt) {
        this.stmt = stmt;
    }

    public BDserver() {
        PG_HOST = "localhost";
        PG_DB = "bd1";
        USER = "user1";
        PWD = "ANTmar02";
    }

    public BDserver(String host, String bd, String user, String pass) {
        PG_HOST = host;
        PG_DB = bd;
        USER = user;
        PWD = pass;
    }

    public void connect() {

        try {

            Class.forName("org.postgresql.Driver");

            // url = "jdbc:postgresql://host:port/database", psql bd1 -U user1 -h localhost
            con = DriverManager.getConnection("jdbc:postgresql://" + PG_HOST + ":5432/" + PG_DB, USER, PWD);
            stmt = con.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Problems setting the connection");

        }
    }

    public int insertTableArtist(artist artist) {
        int artistID = -1;

        String insertQuery = "INSERT INTO artists (name, type, local, performing, approved) VALUES (?, ?, ?, ?, ?) RETURNING id;";
        try (PreparedStatement pstmt = con.prepareStatement(insertQuery)) {
            pstmt.setString(1, artist.getName());
            pstmt.setString(2, artist.getTypeArt());
            pstmt.setString(3, artist.getLocation());
            pstmt.setBoolean(4, artist.getPerforming());
            pstmt.setBoolean(5, artist.getApproved());

            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                artistID = resultSet.getInt("id");
                artist.setArtistID(artistID);
            }

            System.out.println("Inserido artista com id - " + artistID);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Problemas a inserir...");
        }
        return artistID;
    }

    public int insertTableDonatives(donative donative) {
        int donativeID = -1;

        String insertQuery = "INSERT INTO donatives (artistid, name, value) VALUES (?, ?, ?) RETURNING id;";
        try (PreparedStatement pstmt = con.prepareStatement(insertQuery)) {
            pstmt.setInt(1, donative.getArtistID());
            pstmt.setString(2, donative.getDonatorname());
            pstmt.setInt(3, donative.getValue());

            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                donativeID = resultSet.getInt("id");
                donative.setDonativeID(donativeID);
            }

            System.out.println("Inserido Donativo com id - " + donativeID);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Problemas a inserir...");
        }
        return donativeID;
    }

    public List<artist> consultArtists(String fields) {
        List<artist> results = new ArrayList<artist>();
        artist artist = null;
        try {
            String insertQuery = "SELECT * FROM artists " + fields + " ;";
            ResultSet rs = stmt.executeQuery(insertQuery);
            int count = 0;
            while (rs.next()) {
                String name = rs.getString("name");
                String typeArt = rs.getString("type");
                String location = rs.getString("local");
                Boolean performing = rs.getBoolean("performing");
                Boolean approved = rs.getBoolean("approved");
                int id = rs.getInt("id");

                artist = new artist();
                artist.setName(name);
                artist.setTypeArt(typeArt);
                artist.setLocation(location);
                artist.setPerforming(performing);
                artist.setApproved(approved);
                artist.setArtistID(id);

                results.add(artist);
                count++;
            }
            if (count == 0)
                System.out.println("Nenhum artista encontrado");
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Problemas a obter dados ...");
        }
        return results;
    }

    public List<donative> consultDonatives(String fields) {
        List<donative> results = new ArrayList<donative>();
        donative donative = null;
        try {
            String insertQuery = "SELECT * FROM donatives " + fields + " ;";
            ResultSet rs = stmt.executeQuery(insertQuery);
            int count = 0;
            while (rs.next()) {
                String donatorname = rs.getString("name");
                int artistID = rs.getInt("artistid");
                int value = rs.getInt("value");
                int id = rs.getInt("id");

                donative = new donative();
                donative.setArtistID(artistID);
                donative.setDonativeID(id);
                donative.setDonatorname(donatorname);
                donative.setValue(value);

                results.add(donative);
                count++;
            }
            if (count == 0)
                System.out.println("Nenhum Donativo encontrado");
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Problemas a obter dados ...");
        }
        return results;
    }

    public void changeArtistState(Boolean state, int ArtistID) {
        try {
            String insertQuery = "UPDATE artists SET approved=" + state + " WHERE id= " + ArtistID + " ;";

            stmt.executeUpdate(insertQuery);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Problema a atualizar tabela...");

        }

    }

    public void disconnect() { // importante: fechar a ligacao 'a BD
        try {
            stmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
