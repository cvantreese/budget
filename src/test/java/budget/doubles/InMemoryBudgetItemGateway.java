package budget.doubles;

import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import budget.entities.BudgetItem;
import budget.entities.User;
import budget.gateways.BudgetItemGateway;

public class InMemoryBudgetItemGateway extends GatewayUtils<BudgetItem> implements BudgetItemGateway {

	@Override
	public List<BudgetItem> findAllForUserAndBudgetPeriod(User loggedInUser, Month month, Year year) {
		return getEntities().stream().filter(item -> item.getUser().equals(loggedInUser) 
				&& item.getMonth() == month 
				&& item.getYear().getValue() == year.getValue())
				.collect(Collectors.toList());
	}
}
