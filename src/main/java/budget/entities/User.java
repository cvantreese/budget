package budget.entities;

import java.util.UUID;

public class User extends Entity {

	private String username;

	public User(String username) { this.username = username; }

	public String getUsername() { return username; }

	
	
}
