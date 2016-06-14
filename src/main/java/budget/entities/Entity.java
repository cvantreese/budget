package budget.entities;

public class Entity implements Cloneable {
	private String id;
	
	public String getId() { return this.id; }
	public void setId(String id) { this.id = id; }
	
	@Override
	public Entity clone() {
		try {
			return (Entity) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return this;
		}
	}
}
