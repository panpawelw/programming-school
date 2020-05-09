package pl.pjm77.model;

import java.sql.Timestamp;

public class LastSolution {

    private long id;
    private String title;
    private String name;
    private Timestamp modified;

    public LastSolution() {}

    public LastSolution(String title, String name, Timestamp modified) {
        this.title = title;
        this.name = name;
        this.modified = modified;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Timestamp getModified() {
        return modified;
    }
    public void setModified(Timestamp modified) {
        this.modified = modified;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "LastSolution{id=" + this.id + ", title='" + this.title + ", name='" + this.name +
          ", modified=" + this.modified + '}';
    }
}