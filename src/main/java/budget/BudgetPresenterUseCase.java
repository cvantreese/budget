package budget;

import java.time.Month;
import java.time.Year;
import java.util.List;

public class BudgetPresenterUseCase {

	public void requestBudget(User loggedInUser, Month month, Year year, BudgetPresenter presenter) {
		List<BudgetItem> budgetItems = Context.budgetItemGateway.findAllForUserAndBudgetPeriod(loggedInUser, month,
				year);
		List<Transaction> transactions = Context.transactionGateway
				.findAllForUserAndBudgetPeriod(loggedInUser, month, year);
		BudgetPresenterResponseModel responseModel = new BudgetPresenterResponseModel();
		budgetItems.forEach(item -> item.calculateActual(transactions));
		budgetItems.forEach(item -> item.calculateWhatsLeft());
		budgetItems.forEach(item -> responseModel.addBudgetItem(item));
		transactions.forEach(item -> responseModel.addTransaction(item));
		presenter.present(responseModel);
	}

}
