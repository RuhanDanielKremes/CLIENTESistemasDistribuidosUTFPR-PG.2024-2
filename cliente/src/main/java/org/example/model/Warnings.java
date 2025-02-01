package org.example.model;

public class Warnings {
 
    private int id;
    private String title;
    private String description;
    private int category;

    public Warnings() {
        id = 0;
        title = "";
        description = "";
        category = 0;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getCategory() {
        return category;
    }

    @Override
    public String toString() {
        String returnString = "{id: " + this.id;
        if (this.title != null && !this.title.isEmpty()) {
            returnString += ", title: " + this.title;
        }
        if (this.description != null && !this.description.isEmpty()) {
            returnString += ", description: " + this.description;
        }
        return returnString += ", category: " + this.category + "}";
    }

}