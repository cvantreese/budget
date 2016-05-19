package budget;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.Month;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.bechte.junit.runners.context.HierarchicalContextRunner;

@RunWith(HierarchicalContextRunner.class)
public class PresentBudgetAcceptanceTests {

	@Before
	public void setup() {
		Context.userGateway = new InMemoryUserGateway();
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
					usecase.requestBudget(loggedInUser, month, presenter);
					assertEquals(0, presenter.getViewModel().getViewableBudgetItems().size());
				}

			}

		}

	}
}
