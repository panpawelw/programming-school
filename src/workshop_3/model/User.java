package workshop_3.model;

public class User {

	private long id;
	private String name;
	private String email;
	private String password;
	private String group_id;
	
	public User() {}

	public User(String name, String email, String password, String group_id) {
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

	public String getGroup_id() {
		return group_id;
	}

	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}

	public long getId() {
		return id;
	}
}