package com.trabalho2;

import java.sql.*;
import java.sql.Date;
import java.util.*;



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

        String insertQuery = "INSERT INTO artists (name, type, latitude, longitude, performing, approved) VALUES (?, ?, ?, ?, ?, ?) RETURNING id;";
        try (PreparedStatement pstmt = con.prepareStatement(insertQuery)) {
            pstmt.setString(1, artist.getName());
            pstmt.setString(2, artist.getTypeArt());
            pstmt.setDouble(3, artist.getLatitude());
            pstmt.setDouble(4, artist.getLongitude());
            pstmt.setBoolean(5, artist.getPerforming());
            pstmt.setBoolean(6, artist.getApproved());

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

    public int insertTablePerformances(performances P) {
        int performanceID = -1;

        String insertQuery = "INSERT INTO performances (artist_id, latitude, longitude, actuation_date) VALUES ( ?, ?, ?, ?) RETURNING actuation_id;";
        try (PreparedStatement pstmt = con.prepareStatement(insertQuery)) {
            pstmt.setInt(1, P.getArtist_id());
            pstmt.setDouble(2, P.getLatitude());
            pstmt.setDouble(3, P.getLongitude());
            pstmt.setDate(4, P.getperformance_date());

            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                performanceID = resultSet.getInt("actuation_id");
                P.setActuation_id(performanceID);
            }

            System.out.println("Inserida perfroance com id - " + performanceID);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Problemas a inserir...");
        }
        return performanceID;
    }

    public String insertTableUsers(user user) {
        System.out.println(user);
        String insertQuery = "INSERT INTO users (username, email, password, type) VALUES (?, ?, ?, ?);";
        try (PreparedStatement pstmt = con.prepareStatement(insertQuery)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getType());

            if (pstmt.executeUpdate() == 0) {
                throw new Exception("NAO INSERIDO");
            }

            System.out.println("Inserido User com Username - " + user.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Problemas a inserir...");
        }
        return user.getUsername();
    }

    public int insertTableDonatives(donative donative) {
        int donativeID = -1;

        String insertQuery = "INSERT INTO donatives (artistid, donation_date, username, value) VALUES (?, ?, ?, ?) RETURNING id;";
        try (PreparedStatement pstmt = con.prepareStatement(insertQuery)) {
            pstmt.setInt(1, donative.getArtistID());
            pstmt.setDate(2, donative.getDate());
            pstmt.setString(3, donative.getDonatorname());
            pstmt.setInt(4, donative.getValue());

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
                Double latitude = rs.getDouble("latitude");
                Double longitude = rs.getDouble("longitude");
                Boolean performing = rs.getBoolean("performing");
                Boolean approved = rs.getBoolean("approved");
                int id = rs.getInt("id");

                artist = new artist();
                artist.setName(name);
                artist.setTypeArt(typeArt);
                artist.setLongitude(longitude);
                artist.setLatitude(latitude);
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

    public List<performances> consultPerformances(String fields) {
        List<performances> results = new ArrayList<performances>();
        performances p = null;
        try {
            String insertQuery = "SELECT * FROM performances " + fields + " ;";
            ResultSet rs = stmt.executeQuery(insertQuery);
            int count = 0;
            while (rs.next()) {
                Double latitude = rs.getDouble("latitude");
                Double longitude = rs.getDouble("longitude");
                int artist_id = rs.getInt("artist_id");
                Date date = rs.getDate("actuation_date");
                int id = rs.getInt("actuation_id");

                p = new performances();
                p.setLongitude(longitude);
                p.setLatitude(latitude);
                p.setperformance_date(date);
                p.setActuation_id(id);
                p.setArtist_id(artist_id);

                results.add(p);
                count++;
            }
            if (count == 0)
                System.out.println("Nenhuma performance encontrada");
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Problemas a obter dados ...");
        }
        return results;
    }

    public List<user> consultUsers(String fields) {
        List<user> results = new ArrayList<user>();
        user user = null;
        try {
            String insertQuery = "SELECT username, email, type FROM users " + fields + " ;";
            ResultSet rs = stmt.executeQuery(insertQuery);
            int count = 0;
            while (rs.next()) {
                String username = rs.getString("username");
                String email = rs.getString("email");
                String type = rs.getString("type");

                user = new user();
                user.setUsername(username);
                user.setEmail(email);
                user.setType(type);

                results.add(user);
                count++;
            }
            if (count == 0)
                System.out.println("Nenhum user encontrado");
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Problemas a obter dados ...");
        }
        return results;
    }

    public void changeArtist(String set, int ArtistID) {
        try {
            String insertQuery = "UPDATE artists SET " + set + " WHERE id= " + ArtistID + " ;";
            stmt.executeUpdate(insertQuery);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Problema a atualizar tabela...");

        }

    }

    public void changeUserState(String username) {
        try {
            String insertQuery = "UPDATE users SET type='ADMIN' WHERE username=" + username + " ;";
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

    public int registerRating(rating avaliacao) {

        int ratingid = -1;

        String insertQuery = "INSERT INTO rating (artist_id, username, rating) VALUES (?, ?, ?) RETURNING rating_id;";
        try (PreparedStatement pstmt = con.prepareStatement(insertQuery)) {

            pstmt.setInt(1, avaliacao.getArtist_id());
            pstmt.setString(2, avaliacao.getUsername());
            pstmt.setInt(3, avaliacao.getRating());

            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                ratingid = resultSet.getInt("rating_id");
                // avaliacao.setratingid(ratingid);
            }

            System.out.println("Inserido Donativo com id - " + ratingid);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Problemas a inserir...");
        }
        return ratingid;

    }

    public double AverageArtistRating(int artistID) {
        double average  = 0;
        String query = "SELECT SUM(rating) AS total_rating, COUNT(*) AS rating_count " +
                       "FROM rating " +
                       "WHERE artist_id = ? " +
                       "GROUP BY artist_id";

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, artistID);

            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {

                    double totalRating = resultSet.getDouble("total_rating");
                    int ratingCount = resultSet.getInt("rating_count");
                    
                    average = totalRating/ratingCount;
                    System.out.println(totalRating + "/"+ratingCount + "="+average);
                    return average;
                } else {
                    System.out.println("No data found for the artist ID.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
       

        return 0;
    }

}
