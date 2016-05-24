package budget;

import java.math.BigDecimal;

public enum ParentCategory {

	HOUSING(1, "Housing"), GROCERIES(2, "Groceries"), AUTO_EXPENSES(3, "Automobile Expense"), BANKING_ACTIVITY(4, "Banking Activity");

	private int id;
	private String value;

	ParentCategory(int id, String value) {
		this.id = id;
		this.value = value;
	}

	public int getId() {
		return id;
	}

	public String getValue() {
		return value;
	}
}
