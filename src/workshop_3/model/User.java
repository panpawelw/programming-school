package workshop_3.model;

import mindrot.jbcrypt.BCrypt;

public class User {

	private long id;
	private String name;
	private String email;
	private String password;
	private int group_id;
	
	public User() {}

	public User(String name, String email, String password, int group_id) {
		this.name = name;
		this.email = email;
		this.setPassword(password);
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
		this.password = BCrypt.hashpw(password, BCrypt.gensalt());
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
}