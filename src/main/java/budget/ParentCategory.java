package budget;

public enum ParentCategory {

	HOUSING(1), GROCERIES(2), AUTO_EXPENSES(3);

	int id;

	ParentCategory(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
