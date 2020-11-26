package com.panpawelw.model;

import java.util.Objects;

public class User {

    private long id;
    private String name;
    private String email;
    private String password;
    private int group_id;

    public User() {}

    public User(long id) {
        this.id = id;
    }

    public User(String name, String email, String password, int group_id) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.group_id = group_id;
    }

    public User(long id, String name, String email, String password, int group_id) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.group_id = group_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.id + ": " + this.name + " email: " + this.email + " group: " + this.group_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                group_id == user.group_id &&
                Objects.equals(name, user.name) &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, password, group_id);
    }
}
