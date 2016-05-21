package budget;

public enum Category {

	NONE(0), RENT(1), AUTO_LOAN(2);

	private int id;

	Category(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
