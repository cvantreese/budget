package budget;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class BudgetPresenterUseCaseTest {
	
	private BudgetPresenter presenter;

	@Before
	public void setup() {
		presenter = new BudgetPresenter();
	}

	@Test
	public void validateViewModel() {
		User user = new User("A");
		Month month = Month.MAY;
		Year year = Year.of(2016);
		Category category = Category.RENT;
		ParentCategory parentCategory = ParentCategory.HOUSING;
		BigDecimal budgeted = BigDecimal.valueOf(1350).setScale(2, RoundingMode.HALF_UP);
		
		
		BudgetItem newBi = new BudgetItem.BudgetItemBuilder(user, month, year, parentCategory)
				.category(Category.RENT)
				.budgeted(BigDecimal.valueOf(1450))
				.build();
		//Context.budgetItemGateway.save(newBi);
		//User loggedInUser = Context.userGateway.findUserByUsername("A");
		//budgetItems.forEach(item -> budgetItemsResponseModel.add(item));
		
		//List<BudgetItem> budgetItems = Context.budgetItemGateway.findAllForUserAndBudgetPeriod(loggedInUser, month, year);
		BudgetItemsResponseModel responseModel = new BudgetItemsResponseModel();
		responseModel.addBudgetItem(newBi);
		presenter.present(responseModel);
		assertEquals(newBi.getId(), responseModel.getBudgetItems().get(0).getId());
	}
	
}
