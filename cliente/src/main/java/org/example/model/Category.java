package org.example.model;

public class Category {
 
    private int id;
    private String name;

    public Category() {
        id = 0;
        name = "";
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    

    @Override
    public String toString() {
        return "Category [id=" + id + ", name=" + name + "]";
    }
}