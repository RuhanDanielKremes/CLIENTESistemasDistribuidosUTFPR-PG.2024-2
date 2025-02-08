package org.example.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category {
 
    @Expose
    @SerializedName("id")
    private int id;

    @Expose
    @SerializedName("nome")
    private String nome;


    public Category() {
        id = 0;
        nome = "";
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String nome) {
        this.nome = nome;
    }

    public String getName() {
        return nome;
    }

    @Override
    public String toString() {
        String returnString = "{id: " + this.id;
        if (this.nome != null) {
            return returnString += ", nome: " + this.nome + "}";
        }
        return returnString += "}";
    }
    
}