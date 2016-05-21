package budget;

import java.time.Month;
import java.time.Year;
import java.util.List;

public interface BudgetItemGateway {
	
	public BudgetItem save(BudgetItem budgetItem);

	public List<BudgetItem> findAllForUserAndBudgetPeriod(User loggedInUser, Month month, Year year);
	
}
