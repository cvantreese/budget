package budget.gateways;

import java.time.Month;
import java.time.Year;
import java.util.List;

import budget.entities.Transaction;
import budget.entities.User;
import budget.entities.Transaction.TransactionBuilder;

public interface TransactionGateway {

	Transaction save(Transaction transaction);

	List<Transaction> findAllForUserAndBudgetPeriod(User loggedInUser, Month month, Year year);

}
