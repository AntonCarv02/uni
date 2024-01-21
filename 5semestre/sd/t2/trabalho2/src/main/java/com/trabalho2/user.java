package com.trabalho2;
public class user implements java.io.Serializable{
    private String username;
    private String email;
    private String password;
    private String type;

    
    public user() {
        super();
    }

    public user(String username, String email, String password, String type) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.type = type;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "user [username=" + username + ", email=" + email + ", password=" + password + ", type=" + type + "]";
    }


}