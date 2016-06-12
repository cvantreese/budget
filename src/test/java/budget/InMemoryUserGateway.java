package budget;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InMemoryUserGateway extends GatewayUtils<User> implements UserGateway {

	public User findUserByUsername(String username) {
		User user = getEntities().stream().filter(localUser -> localUser.getUsername().equals(username)).findFirst().get();
		return user;
	}



}
