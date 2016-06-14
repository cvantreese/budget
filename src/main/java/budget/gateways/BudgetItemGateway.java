package budget.gateways;

import java.time.Month;
import java.time.Year;
import java.util.List;

import budget.entities.BudgetItem;
import budget.entities.User;

public interface BudgetItemGateway {
	
	public BudgetItem save(BudgetItem budgetItem);

	public List<BudgetItem> findAllForUserAndBudgetPeriod(User loggedInUser, Month month, Year year);
	
}
