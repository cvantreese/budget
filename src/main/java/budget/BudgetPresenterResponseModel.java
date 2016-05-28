package budget;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class BudgetPresenterResponseModel {
	
	public Set<String> parentCategories = new TreeSet<>();
	public List<BudgetItem> budgetItems = new ArrayList<>();
	public List<Transaction> transactions = new ArrayList<>();

	public void addParentCategory(String parentCategory) {
		parentCategories.add(parentCategory);
	}

	public void addBudgetItem(BudgetItem budgetItem) {
		budgetItems .add(budgetItem);
	}

	public void addTransaction(Transaction transaction) {
		transactions.add(transaction);
	}

}
