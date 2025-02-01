package org.example.model;

public class Warnings {
 
    private int id;
    private String title;
    private String description;
    private Category category;

    public Warnings() {
        id = 0;
        title = "";
        description = "";
        category = new Category();
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

    public void setCategoryObject(Category category) {
        this.category = category;
    }

    public Category getCategoryObject() {
        return category;
    }

    public String getCategory() {
        return category.getName();
    }

    public void setCategory(String category) {
        this.category.setName(category);
    }

    public void setCategoryId(int id) {
        this.category.setId(id);
    }

    public int getCategoryId(int id) {
        return this.category.getId();
    }

}