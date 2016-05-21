package budget;

import java.util.ArrayList;
import java.util.List;

import budget.BudgetPresenterViewModel.ViewableBudgetItem;

public class BudgetPresenter {
	
	BudgetPresenterViewModel viewModel;

	public BudgetPresenterViewModel getViewModel() {
		return viewModel; 
	}

	private ViewableBudgetItem makeViewable(BudgetItem item) {
		ViewableBudgetItem viewableBudgetItem = new ViewableBudgetItem();
		viewableBudgetItem.id = item.getId();
		return viewableBudgetItem;
	}

	public void present(BudgetItemsResponseModel responseModel) {
		viewModel = new BudgetPresenterViewModel();
		responseModel.getBudgetItems().forEach(item -> viewModel.addModel(makeViewable(item)));
	}

}
