package budget;

import java.util.ArrayList;
import java.util.List;

import budget.BudgetPresenterViewModel.ViewableBudgetItem;

public class BudgetPresenterViewModel {

	List<ViewableBudgetItem> viewableBudgetItems = new ArrayList<>();

	public List<ViewableBudgetItem> getViewableBudgetItems() {
		return viewableBudgetItems;
	}

	public void addModel(ViewableBudgetItem model) {
		viewableBudgetItems.add(model);
	}

	public static class ViewableBudgetItem {

		public String id;
		
		public static class ViewableTransaction {
			
			
			
		}

	}
	
	

}
