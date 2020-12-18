package com.panpawelw.model;

import java.sql.Timestamp;
import java.util.Objects;

public class Solution {

    private long id;
    private Timestamp created;
    private Timestamp updated;
    private String description;
    private int exercise_id;
    private long user_id;

    public Solution() {}

    public Solution(long id) {
        this.id = id;
    }

    public Solution(String description, int exercise_id, long user_id) {
        this.description = description;
        this.exercise_id = exercise_id;
        this.user_id = user_id;
    }

    public Solution(Timestamp created, Timestamp updated, String description,
                    int exercise_id, long user_id) {
        this.created = created;
        this.updated = updated;
        this.description = description;
        this.exercise_id = exercise_id;
        this.user_id = user_id;
    }

    public Solution(long id, Timestamp created, Timestamp updated, String description,
                    int exercise_id, long user_id) {
        this.id = id;
        this.created = created;
        this.updated = updated;
        this.description = description;
        this.exercise_id = exercise_id;
        this.user_id = user_id;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getExercise_id() {
        return exercise_id;
    }

    public void setExercise_id(int exercise_id) {
        this.exercise_id = exercise_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.id + ": " + this.description + " created: " + this.created + " updated: "
                + this.updated + " exercise: " + this.exercise_id + " user: " + this.user_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Solution solution = (Solution) o;
        return id == solution.id && exercise_id == solution.exercise_id && user_id == solution.user_id && Objects.equals(created, solution.created) && Objects.equals(updated, solution.updated) && Objects.equals(description, solution.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, created, updated, description, exercise_id, user_id);
    }
}
