package budget;

public class Entity implements Cloneable {
	private String id;
	
	public String getId() { return this.id; }
	public void setId(String id) { this.id = id; }
	
	@Override
	protected Entity clone() {
		try {
			return (Entity) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return this;
		}
	}
}
