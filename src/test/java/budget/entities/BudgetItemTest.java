package budget.entities;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import budget.entities.BudgetItem;
import budget.entities.Category;
import budget.entities.Transaction;
import budget.entities.User;

public class BudgetItemTest {
	
	User chris;
	Month month;
	Year year;
	Category category;
	BudgetItem budgetItem;
	List<Transaction> transactions;
	
	@Before
	public void setup() {
		chris = new User("Chris");
		month = Month.MAY;
		year = Year.of(2016);
		category = Category.GROCERIES;
		budgetItem = new BudgetItem.BudgetItemBuilder(chris , month, year, category)
				.budgeted(BigDecimal.valueOf(900))
				.build();	
		transactions = new ArrayList<>();
	}

	@Test
	public void calculateActual_noTransactionsShouldReturnBudgetedAmount() {
		budgetItem.calculateActual(transactions);
		assertEquals(BigDecimal.ZERO, budgetItem.getActual());
		
	}
	
	@Test
	public void calculateActual_nullShouldBeTreatedAsNoTransactions() {
		budgetItem.calculateActual(null);
		assertEquals(BigDecimal.ZERO, budgetItem.getActual());
	}
	
	@Test
	public void calculateActual_oneTransaction_subtractAmountsFromBudgeted() {
		Transaction t1 = new Transaction.TransactionBuilder(chris, LocalDate.of(2016, 5, 5))
				.amount(BigDecimal.valueOf(56.76))
				.category(Category.GROCERIES)
				.build() ;
		transactions.add(t1);
		budgetItem.calculateActual(transactions);
		assertEquals(BigDecimal.valueOf(56.76), budgetItem.getActual());
	}
	
	@Test
	public void calculateActual_multipleTransactions_subtractAmountsFromBudgeted() {
		Transaction t1 = new Transaction.TransactionBuilder(chris, LocalDate.of(2016, 5, 5))
				.amount(BigDecimal.valueOf(56.76))
				.category(Category.GROCERIES)
				.build() ;
		transactions.add(t1);
		Transaction t2 = new Transaction.TransactionBuilder(chris, LocalDate.of(2016, 5, 5))
				.amount(BigDecimal.valueOf(154.78))
				.category(Category.GROCERIES)
				.build() ;
		transactions.add(t2);
		budgetItem.calculateActual(transactions);
		assertEquals(BigDecimal.valueOf(211.54), budgetItem.getActual());
	}
	
	
	
}
