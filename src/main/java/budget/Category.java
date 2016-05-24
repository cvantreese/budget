package budget;

import java.math.BigDecimal;

public enum Category {

	NONE(0, "Uncategorized"), RENT(1, "Rent"), AUTO_LOAN(2, "Auto Loan"), SERVICE_FEE(3, "Service Fee");

	private int id;
	private String value;


	Category(int id, String value) {
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
