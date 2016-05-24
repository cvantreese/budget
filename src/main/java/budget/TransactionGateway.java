package budget;

import java.time.Month;
import java.time.Year;
import java.util.List;

import budget.Transaction.TransactionBuilder;

public interface TransactionGateway {

	Transaction save(Transaction transaction);

	List<Transaction> findAllForUserAndBudgetPeriod(User loggedInUser, Month month, Year year);

}
