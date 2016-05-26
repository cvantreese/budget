package budget;

import java.math.BigDecimal;
import java.time.Month;
import java.time.Year;

public class BudgetItem extends Entity {

	private User user;
	private Month month;
	private Year year;
	private Category category;
	private BigDecimal budgeted;
	
	private BudgetItem() {}
	
	private BudgetItem(BudgetItemBuilder budgetItemBuilder) {
		this.user = budgetItemBuilder.user;
		this.month = budgetItemBuilder.month;
		this.year = budgetItemBuilder.year;
		this.category = budgetItemBuilder.category;
		this.budgeted = budgetItemBuilder.budgeted;
	}
	
	

	@Override
	public String toString() {
		return "BudgetItem [user=" + user + ", month=" + month + ", year=" + year + ", category=" + category
				+ ", budgeted=" + budgeted + "]";
	}



	public static class BudgetItemBuilder {
		private User user;
		private Month month;
		private Year year;
		private Category category;
		private BigDecimal budgeted;

		public BudgetItemBuilder(User user, Month month, Year year, Category category) {
			this.user = user;
			this.month = month;
			this.year = year;
			this.category = category;
			this.budgeted = BigDecimal.ZERO;
		}

		public BudgetItemBuilder budgeted(BigDecimal budgeted) {
			this.budgeted = budgeted;
			return this;
		}

		public BudgetItem build() {
			return new BudgetItem(this);
		}

	}

	public User getUser() {
		return user;
	}

	public Month getMonth() {
		return month;
	}

	public Year getYear() {
		return year;
	}
	
	public Category getCategory() {
		return category;
	}

}
