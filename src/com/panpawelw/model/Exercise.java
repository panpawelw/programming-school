package com.panpawelw.model;

public class Exercise {

    private int id;
    private String title;
    private String description;

    public Exercise() {}

    public Exercise(int id) {
        this.id = id;
    }

    public Exercise(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "id: " + this.id + " title: " + this.title + " description: " + this.description;
    }
}