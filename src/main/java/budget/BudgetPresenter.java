package budget;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import budget.BudgetPresenterViewModel.ViewableBudgetItem;
import budget.BudgetPresenterViewModel.ViewableTransaction;

public class BudgetPresenter {
	
	BudgetPresenterViewModel viewModel;

	public BudgetPresenterViewModel getViewModel() {
		return viewModel; 
	}

	public void present(BudgetPresenterResponseModel responseModel) {
		viewModel = new BudgetPresenterViewModel();
		
		responseModel.parentCategories.forEach(item -> viewModel.addParentCategory(item));
		responseModel.budgetItems.forEach(item -> viewModel.addViewableBudgetItem(makeViewable(item)));
		responseModel.transactions.forEach(item -> viewModel.addViewableTransaction(makeViewable(item)));;
	}

	private ViewableTransaction makeViewable(Transaction transaction) {
		ViewableTransaction viewableTransaction = new ViewableTransaction();
		viewableTransaction.id = transaction.getId();
		viewableTransaction.user = transaction.getUser().getId();
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		viewableTransaction.date = transaction.getDate().format(dateFormatter);
		viewableTransaction.amount = transaction.getAmount().toString();
		viewableTransaction.parentCategory = transaction.getCategory().parentCategory;
		viewableTransaction.category = transaction.getCategory().category;
		viewableTransaction.description = transaction.getDescription();
		return viewableTransaction;
	}

	private ViewableBudgetItem makeViewable(BudgetItem budgetItem) {
		ViewableBudgetItem viewableBudgetItem = new ViewableBudgetItem();
		viewableBudgetItem.budgeted = budgetItem.getBudgeted().toString();
		viewableBudgetItem.category = budgetItem.getCategory().category;
		viewableBudgetItem.id = budgetItem.getId();
		viewableBudgetItem.type = (budgetItem.getType() == BudgetItem.Type.EXPENSE ? "Expense" : "Income");
		viewableBudgetItem.actual = budgetItem.getActual().toString();
		viewableBudgetItem.whatsLeft = budgetItem.getWhatsLeft().toString();
		return viewableBudgetItem;
	}

}
