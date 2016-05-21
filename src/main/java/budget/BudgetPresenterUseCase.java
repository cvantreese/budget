package budget;

import java.time.Month;
import java.time.Year;
import java.util.List;

public class BudgetPresenterUseCase {

	public void requestBudget(User loggedInUser, Month month, Year year, BudgetPresenter presenter) {
		List<BudgetItem> budgetItems = Context.budgetItemGateway.findAllForUserAndBudgetPeriod(loggedInUser, month,
				year);
		BudgetItemsResponseModel responseModel = new BudgetItemsResponseModel();
		budgetItems.forEach(item -> responseModel.addBudgetItem(item));
		presenter.present(responseModel);
	}

}
