package budget;

import java.math.BigDecimal;
import java.time.Month;
import java.time.Year;

public class BudgetItem extends Entity {

	private User user;
	private Month month;
	private Year year;
	private Category category;
	private ParentCategory parentCategory;
	private BigDecimal budgeted;
	
	private BudgetItem() {}
	
	private BudgetItem(BudgetItemBuilder budgetItemBuilder) {
		this.user = budgetItemBuilder.user;
		this.month = budgetItemBuilder.month;
		this.parentCategory = budgetItemBuilder.parentCategory;
		this.category = budgetItemBuilder.category;
		this.budgeted = budgetItemBuilder.budgeted;
	}

	public static class BudgetItemBuilder {
		private User user;
		private Month month;
		private Year year;
		private Category category;
		private ParentCategory parentCategory;
		private BigDecimal budgeted;

		public BudgetItemBuilder(User user, Month month, Year year, ParentCategory parentCategory) {
			this.user = user;
			this.month = month;
			this.parentCategory = parentCategory;
			this.category = Category.NONE;
			this.budgeted = BigDecimal.ZERO;
		}

		public BudgetItemBuilder category(Category category) {
			this.category = category;
			return this;
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

}
