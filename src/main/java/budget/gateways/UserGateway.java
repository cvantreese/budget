package budget.gateways;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import budget.entities.User;

public interface UserGateway {

	public User save(User user);
	public User findUserByUsername(String username);
	
}
