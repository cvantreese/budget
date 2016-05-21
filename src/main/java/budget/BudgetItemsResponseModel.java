package budget;

import java.util.ArrayList;
import java.util.List;

public class BudgetItemsResponseModel {

	private List<BudgetItem> budgetItemsResponseModel;
	
	public BudgetItemsResponseModel() {
		budgetItemsResponseModel = new ArrayList<>();
	}
	
	public void addBudgetItem(BudgetItem budgetItem) {
		budgetItemsResponseModel.add(budgetItem);
	}

	public List<BudgetItem> getBudgetItems() {
		return budgetItemsResponseModel;
	}
	
}
