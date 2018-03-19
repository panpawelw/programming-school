package workshop_3.model;

import java.sql.Timestamp;

public class Solution {
	
	private long id;
	private Timestamp created;
	private Timestamp updated;
	private String description;
	private int exercise_id;
	private long user_id;
	
	public Solution() {}
	
	public Solution(String description, int exercise_id, long user_id) {
		this.description = description;
		this.exercise_id = exercise_id;
		this.user_id = user_id;
	}
	
	public Solution(Timestamp created, Timestamp updated, String description, int exercise_id, long user_id) {
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
		String solutionToString = this.id + ": " + this.description + " created: " + this.created + " updated: " + this.updated + " exercise: " + this.exercise_id + " user: " + this.user_id;
		return solutionToString;
	}
}