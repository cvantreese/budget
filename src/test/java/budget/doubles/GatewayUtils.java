package budget.doubles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import budget.entities.Entity;

public class GatewayUtils<T extends Entity> {
	
	private HashMap<String, T> entities;
	
	public GatewayUtils() {
		this.entities = new HashMap<>();
	}
	
	public List<T> getEntities() {
		List<T> clonedEntities	 = new ArrayList<>();
		entities.values().forEach(entity -> addCloneToList(entity, clonedEntities));
		return clonedEntities;
	}

	private void addCloneToList(T entity, List<T> clonedEntities) {
		clonedEntities.add((T) entity.clone());
	}
	
	public T save(T entity) {
		if (entity.getId() == null) {
			entity.setId(UUID.randomUUID().toString());
		}
		String id = entity.getId();
		saveCloneInMap(id, entity);
		return entity;
	}

	@SuppressWarnings("unchecked")
	private void saveCloneInMap(String id, T entity) {
		entities.put(id, (T) entity.clone());
	}
	
	public void delete(T entity) {
		entities.remove(entity.getId());
	}

}
