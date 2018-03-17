package workshop_3.model;

public class Group {

	private int id;
	private String name;
	
	public Group() {}
	
	public Group(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}
}