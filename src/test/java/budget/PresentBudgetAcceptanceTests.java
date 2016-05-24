package budget;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import budget.BudgetPresenterViewModel.ViewableBudgetItem;
import budget.BudgetPresenterViewModel.ViewableBudgetItem.ViewableTransaction;
import de.bechte.junit.runners.context.HierarchicalContextRunner;

@RunWith(HierarchicalContextRunner.class)
public class PresentBudgetAcceptanceTests {

	@Before
	public void setup() {
		Context.userGateway = new InMemoryUserGateway();
		Context.budgetItemGateway = new InMemoryBudgetItemGateway();
		Context.gatekeeper = new Gatekeeper();
		Context.transactionGateway = new InMemoryTransactionGateway();
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
					User loggedInUser = Context.gatekeeper.loggedInUser;
					Context.budgetItemGateway.save(new BudgetItem.BudgetItemBuilder(loggedInUser
							, Month.MAY
							, Year.of(2016)
							, ParentCategory.HOUSING)
							.category(Category.RENT)
							.budgeted(BigDecimal.valueOf(1350).setScale(2, BigDecimal.ROUND_HALF_UP))
							.build());
					Context.budgetItemGateway.save(new BudgetItem.BudgetItemBuilder(loggedInUser
							, Month.MAY
							, Year.of(2016)
							, ParentCategory.GROCERIES)
							.budgeted(BigDecimal.valueOf(900).setScale(2, BigDecimal.ROUND_HALF_UP))
							.build());
					Context.budgetItemGateway.save(new BudgetItem.BudgetItemBuilder(loggedInUser
							, Month.MAY
							, Year.of(2016)
							, ParentCategory.AUTO_EXPENSES)
							.category(Category.AUTO_LOAN)
							.budgeted(BigDecimal.valueOf(297).setScale(2, BigDecimal.ROUND_HALF_UP))
							.build());
					Context.budgetItemGateway.save(new BudgetItem.BudgetItemBuilder(loggedInUser
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
					
					Context.transactionGateway.save(new Transaction.TransactionBuilder(loggedInUser
							, LocalDate.of(2016, Month.MAY, 2)
							, ParentCategory.GROCERIES)
							.category(Category.NONE)
							.description("Marianos")
							.amount(BigDecimal.valueOf(115.63)
									.setScale(2, BigDecimal.ROUND_HALF_UP))
							.build());
					Context.transactionGateway.save(new Transaction.TransactionBuilder(loggedInUser
							, LocalDate.of(2016, Month.MAY, 1)
							, ParentCategory.HOUSING)
							.category(Category.RENT)
							.description("Marianos")
							.amount(BigDecimal.valueOf(115.63)
									.setScale(2, BigDecimal.ROUND_HALF_UP))
							.build());
					Context.transactionGateway.save(new Transaction.TransactionBuilder(loggedInUser
							, LocalDate.of(2016, Month.MAY, 5)
							, ParentCategory.AUTO_EXPENSES)
							.category(Category.AUTO_LOAN)
							.description("Marianos")
							.amount(BigDecimal.valueOf(115.63)
									.setScale(2, BigDecimal.ROUND_HALF_UP))
							.build());
					Context.transactionGateway.save(new Transaction.TransactionBuilder(loggedInUser
							, LocalDate.of(2016, Month.MAY, 5)
							, ParentCategory.BANKING_ACTIVITY)
							.category(Category.SERVICE_FEE)
							.amount(BigDecimal.valueOf(25.00)
									.setScale(2, BigDecimal.ROUND_HALF_UP))
							.build());
				}

				@Test
				public void correctBudgetItemsShouldShow() {
					User loggedInUser = Context.gatekeeper.loggedInUser;
					BudgetPresenter presenter = new BudgetPresenter();
					BudgetPresenterUseCase usecase = new BudgetPresenterUseCase();
					Month month = Month.MAY;
					Year year = Year.of(2016);
					usecase.requestBudget(loggedInUser, month, year, presenter);
					
					List<BudgetItem> budgetItems = Context.budgetItemGateway.findAllForUserAndBudgetPeriod(loggedInUser, month, year);
					List<Transaction> transactions = Context.transactionGateway.findAllForUserAndBudgetPeriod(loggedInUser, month, year);
					
					Set<ParentCategory> parentCategorySet = new HashSet<>();
					Set<Category> categorySet = new HashSet<>();
					budgetItems.forEach(item -> { parentCategorySet.add(item.getParentCategory()); categorySet.add(item.getCategory()); } );
					transactions.forEach(item -> { parentCategorySet.add(item.getParentCategory()); categorySet.add(item.getCategory()); } );
					
					List<ParentCategory> parentCategoryList = new ArrayList<>();
					parentCategorySet.forEach(item -> parentCategoryList.add(item));
					List<Category> categoryList = new ArrayList<>();
					categoryList.forEach(item -> categoryList.add(item));
					

					Collections.sort(parentCategoryList, new Comparator<ParentCategory>() {
						public int compare(ParentCategory o1, ParentCategory o2) {
							return o1.getValue().compareTo(o2.getValue());
						}
					});
					
					Collections.sort(categoryList, new Comparator<Category>() {
						public int compare(Category o1, Category o2) {
							return o1.getValue().compareTo(o2.getValue());
						}
					});
				
					
					assertEquals(null, parentCategoryList);
					
					/*Map<Category, List<ViewableTransaction>> viewableTransactions = new HashMap<>();
					transactions.forEach(item -> viewableTransactions.add(new ViewableTransaction(item.getId(), item.getCategory(), item.getParentCategory(), item.getDescription(), item.getDate(), item.getAmount())));
									
					List<ViewableBudgetItem> viewableBudgetItems = new ArrayList<>();
					budgetItems.forEach(item -> viewableBudgetItems.add(new ViewableBudgetItem(item.getId, item.getCategory(), item.getParentCategory(), item.getDescription(), item.getBudgeted())));
					
					viewableBudgetItems.forEach(action);
					
					expectedViewModel.addModel(new ViewBudgetItem());*/
				}

			}

		}

	}
}
