package org.example.model;

public class User {

    private int id;
    private String name;
    private String ra;
    private String password;
    private String role;
    private String token;

    public User() {
        this.id = 0;
        this.name = "";
        this.ra = "";
        this.password = "";
        this.role = "user";
        this.token = "";
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRa() {
        return ra;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRa(String ra) {
        this.ra = ra;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        String returnString;
        returnString = "{";
        returnString = id == 0 ? "" : "ID: " + id;
        returnString = name == "" ? returnString
                : returnString.length() > 1 ? returnString + ", \"Name\": \"" + name + "\""
                        : "\"Name\": \"" + name + "\"";
        returnString = ra == "" ? returnString
                : returnString.length() > 1 ? returnString + ", \"RA\": \"" + ra + "\""
                        : "\"RA\": \"" + ra + "\"";
        returnString = password == "" ? returnString
                : returnString.length() > 1 ? returnString + ", \"Password\": \"" + password + "\""
                        : "\"Password\": \"" + password + "\"";
        returnString = role == "" ? returnString
                : returnString.length() > 1 ? returnString + ", \"Role\": \"" + role + "\""
                        : "\"Role\": \"" + role + "\"";
        returnString += "}";
        return returnString;
    }

}
