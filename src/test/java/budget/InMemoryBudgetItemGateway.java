package budget;

import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class InMemoryBudgetItemGateway implements BudgetItemGateway {

	List<BudgetItem> budgetItems = new ArrayList<>();
	
	public BudgetItem save(BudgetItem budgetItem) {
		if (budgetItem.getId() == null) {
			budgetItem.setId(UUID.randomUUID().toString());
		}
		budgetItems.add(budgetItem);
		return budgetItem;
	}

	@Override
	public List<BudgetItem> findAllForUserAndBudgetPeriod(User loggedInUser, Month month, Year year) {
		return budgetItems.stream().filter(item -> item.getUser().equals(loggedInUser) 
				&& item.getMonth() == month 
				&& item.getYear() == year)
				.collect(Collectors.toList());
	}
}
