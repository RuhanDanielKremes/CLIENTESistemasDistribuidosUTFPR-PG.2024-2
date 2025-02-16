package org.example.model;

public class User {

    private int id;
    private String nome;
    private String ra;
    private String senha;
    private String role;
    private String token;

    public User() {
        this.id = 0;
        this.nome = "";
        this.ra = "";
        this.senha = "";
        this.role = "";
        this.token = "";
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return nome;
    }

    public String getRa() {
        return ra;
    }

    public String getPassword() {
        return senha;
    }

    public String getRole() {
        return role;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.nome = name;
    }

    public void setRa(String ra) {
        this.ra = ra;
    }

    public void setPassword(String password) {
        this.senha = password;
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
        returnString = id == 0 ? returnString : returnString + "id: " + id;
        returnString = nome == "" ? returnString
                : returnString.length() > 1 ? returnString + ", \"nome\": \"" + nome + "\""
                        : returnString + "\"nome\": \"" + nome + "\"";
        returnString = ra == "" ? returnString
                : returnString.length() > 1 ? returnString + ", \"ra\": \"" + ra + "\""
                        : returnString + "\"ra\": \"" + ra + "\"";
        returnString = senha == "" ? returnString
                : returnString.length() > 1 ? returnString + ", \"senha\": \"" + senha + "\""
                        : returnString + "\"senha\": \"" + senha + "\"";
        returnString = role == "" ? returnString
                : returnString.length() > 1 ? returnString + ", \"role\": \"" + role + "\""
                        : returnString + "\"role\": \"" + role + "\"";
        returnString += "}";
        return returnString;
    }

}
