package budget;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class UserGateway {

	private List<User> users = new ArrayList<User>();

	public User save(User user) {
		if (user.getId() == null) {
			user.setId(UUID.randomUUID().toString());
		}
		users.add(user);
		return user;
	}

	public User findUserByUsername(String username) {
		User user = users.stream().filter(localUser -> localUser.getUsername().equals(username)).findFirst().get();
		return user;
	}

}
