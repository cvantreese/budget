package budget;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.bechte.junit.runners.context.HierarchicalContextRunner;

@RunWith(HierarchicalContextRunner.class)
public class PresentBudgetAcceptanceTests {

	@Before
	public void setup() {
		Context.userGateway = new InMemoryUserGateway();
		Context.budgetItemGateway = new InMemoryBudgetItemGateway();
		Context.gatekeeper = new Gatekeeper();
	}

	public class GivenUserA {

		@Before
		public void setup() {
			Context.userGateway.save(new User("A"));
		}

		public class GivenUserALoggedIn {

			@Before
			public void setup() {
				Context.gatekeeper.loggedInUser = Context.userGateway.findUserByUsername("A");
			}

			public class WhenNoBudgetItemsOrTransactionsExist {

				@Test
				public void noBudgetItemsOrTransactionsExist() {
					User loggedInUser = Context.gatekeeper.loggedInUser;
					BudgetPresenter presenter = new BudgetPresenter();
					BudgetPresenterUseCase usecase = new BudgetPresenterUseCase();
					Month month = LocalDate.now().getMonth();
					Year year = Year.of(LocalDate.now().getYear());
					usecase.requestBudget(loggedInUser, month, year, presenter);
					assertEquals(0, presenter.getViewModel().getViewableBudgetItems().size());
				}

			}
			
			public class WhenSomeBudgetItemsExist {
				
				@Before
				public void setup() {
					Context.budgetItemGateway.save(new BudgetItem.BudgetItemBuilder(new User("A")
							, Month.MAY
							, Year.of(2016)
							, ParentCategory.HOUSING)
							.category(Category.RENT)
							.budgeted(BigDecimal.valueOf(1350).setScale(2, BigDecimal.ROUND_HALF_UP))
							.build());
					Context.budgetItemGateway.save(new BudgetItem.BudgetItemBuilder(new User("A")
							, Month.MAY
							, Year.of(2016)
							, ParentCategory.GROCERIES)
							.budgeted(BigDecimal.valueOf(900).setScale(2, BigDecimal.ROUND_HALF_UP))
							.build());
					Context.budgetItemGateway.save(new BudgetItem.BudgetItemBuilder(new User("A")
							, Month.MAY
							, Year.of(2016)
							, ParentCategory.AUTO_EXPENSES)
							.category(Category.AUTO_LOAN)
							.budgeted(BigDecimal.valueOf(297).setScale(2, BigDecimal.ROUND_HALF_UP))
							.build());
					Context.budgetItemGateway.save(new BudgetItem.BudgetItemBuilder(new User("A")
							, Month.APRIL
							, Year.of(2016)
							, ParentCategory.HOUSING)
							.category(Category.RENT)
							.budgeted(BigDecimal.valueOf(1350).setScale(2, BigDecimal.ROUND_HALF_UP))
							.build());
					Context.budgetItemGateway.save(new BudgetItem.BudgetItemBuilder(new User("B")
							, Month.MAY
							, Year.of(2016)
							, ParentCategory.HOUSING)
							.category(Category.RENT)
							.budgeted(BigDecimal.valueOf(1950).setScale(2, BigDecimal.ROUND_HALF_UP))
							.build());
					
					//TODO Context.transactionGateway.save(new Transaction.TransactionBuilder(new User("A")))
				}

				@Test
				public void correctBudgetItemsShouldShow() {
					User loggedInUser = Context.gatekeeper.loggedInUser;
					BudgetPresenter presenter = new BudgetPresenter();
					BudgetPresenterUseCase usecase = new BudgetPresenterUseCase();
					Month month = LocalDate.now().getMonth();
					Year year = Year.of(LocalDate.now().getYear());
					usecase.requestBudget(loggedInUser, month, year, presenter);
					//TODO finish this test case
				}

			}

		}

	}
}
