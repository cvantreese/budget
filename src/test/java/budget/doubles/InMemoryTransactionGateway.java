package budget.doubles;

import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import budget.entities.Transaction;
import budget.entities.User;
import budget.entities.Transaction.TransactionBuilder;
import budget.gateways.TransactionGateway;

public class InMemoryTransactionGateway extends GatewayUtils<Transaction> implements TransactionGateway {

	@Override
	public List<Transaction> findAllForUserAndBudgetPeriod(User loggedInUser, Month month, Year year) {
		return getEntities().stream().filter(item -> item.getUser().equals(loggedInUser) 
				&& item.getDate().getMonth() == month 
				&& item.getDate().getYear() == year.getValue())
				.collect(Collectors.toList());
	}



}
