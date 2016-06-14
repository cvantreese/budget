package budget.entities;

import java.math.BigDecimal;

public enum Category {

	NONE("Uncategorized", "Uncategorized")
	, RENT("Rent", "Housing")
	, AUTO_LOAN("Auto Loan", "Auto Expenses")
	, FUEL("Fuel", "Auto Expenses")
	, SERVICE_FEE("Service Fee", "Banking Activity")
	, GROCERIES("", "Groceries")
	, HOME_MAINTENANCE("Home Maintenance", "Housing")
	, TRAVEL("", "Travel");

	public String category;
	public String parentCategory;


	Category(String category, String parentCategory) {
		this.category = category;
		this.parentCategory = parentCategory;
	}
	
}
