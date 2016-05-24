package budget;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;

import budget.Transaction.TransactionBuilder;

public class Transaction extends Entity {

	private User user;
	private LocalDate date;
	private ParentCategory parentCategory;
	private Category category;
	private BigDecimal amount;

	public Transaction(TransactionBuilder transactionBuilder) {
		this.user = transactionBuilder.user;
		this.date = transactionBuilder.date;
		this.parentCategory = transactionBuilder.parentCategory;
		this.category = transactionBuilder.category;
		this.amount = transactionBuilder.amount;
		
	}
	
	public ParentCategory getParentCategory() {
		return parentCategory;
	}
	
	public Category getCategory() {
		return category;
	}
	
	public LocalDate getDate() {
		return date;
	}
	
	public User getUser() {
		return user;
	}


	public static class TransactionBuilder {

		private User user;
		private LocalDate date;
		private ParentCategory parentCategory;
		private Category category;
		private String description;
		private BigDecimal amount;

		public TransactionBuilder(User user, LocalDate date, ParentCategory parentCategory) {
			this.user = user;
			this.date = date;
			this.parentCategory = parentCategory;
			this.category = Category.NONE;
			this.description = "";
			this.amount = BigDecimal.ZERO;
		}

		public TransactionBuilder description(String description) {
			this.description = description;
			return this;
		}

		public TransactionBuilder amount(BigDecimal amount) {
			this.amount = amount;
			return this;
		}
		
		public TransactionBuilder category(Category category) {
			this.category = category;
			return this;
		}

		public Transaction build() {
			return new Transaction(this);
		}

	}

}
