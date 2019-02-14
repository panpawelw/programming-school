package programmingSchool.model;

import java.sql.Timestamp;

public class LastSolution {

	private String title;
	private String name;
	private Timestamp modified;
	private long id;
	
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
}