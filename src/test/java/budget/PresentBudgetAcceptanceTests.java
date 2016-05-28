package budget;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.BiFunction;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import budget.BudgetPresenterViewModel.ViewableBudgetItem;
import budget.BudgetPresenterViewModel.ViewableTransaction;
import de.bechte.junit.runners.context.HierarchicalContextRunner;

@RunWith(HierarchicalContextRunner.class)
public class PresentBudgetAcceptanceTests {
	
	DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

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
					assertEquals(0, presenter.getViewModel().viewableBudgetItems.size());
				}

			}

			public class WhenSomeBudgetItemsExist {

				@Before
				public void setup() {
					User loggedInUser = Context.gatekeeper.loggedInUser;
					Context.budgetItemGateway.save(
							new BudgetItem.BudgetItemBuilder(loggedInUser, Month.MAY, Year.of(2016), Category.RENT)
									.budgeted(BigDecimal.valueOf(1350).setScale(2, BigDecimal.ROUND_HALF_UP)).build());
					Context.budgetItemGateway.save(
							new BudgetItem.BudgetItemBuilder(loggedInUser, Month.MAY, Year.of(2016), Category.GROCERIES)
									.budgeted(BigDecimal.valueOf(900).setScale(2, BigDecimal.ROUND_HALF_UP)).build());
					Context.budgetItemGateway.save(
							new BudgetItem.BudgetItemBuilder(loggedInUser, Month.MAY, Year.of(2016), Category.AUTO_LOAN)
									.budgeted(BigDecimal.valueOf(297).setScale(2, BigDecimal.ROUND_HALF_UP)).build());
					Context.budgetItemGateway.save(
							new BudgetItem.BudgetItemBuilder(loggedInUser, Month.APRIL, Year.of(2016), Category.RENT)
									.budgeted(BigDecimal.valueOf(1350).setScale(2, BigDecimal.ROUND_HALF_UP)).build());
					Context.budgetItemGateway.save(new BudgetItem.BudgetItemBuilder(loggedInUser, Month.MAY,
							Year.of(2016), Category.HOME_MAINTENANCE)
									.budgeted(BigDecimal.valueOf(675).setScale(2, BigDecimal.ROUND_HALF_UP)).build());
					Context.budgetItemGateway.save(
							new BudgetItem.BudgetItemBuilder(loggedInUser, Month.MAY, Year.of(2016), Category.TRAVEL)
									.budgeted(BigDecimal.valueOf(600).setScale(2, BigDecimal.ROUND_HALF_UP)).build());


					Context.transactionGateway
							.save(new Transaction.TransactionBuilder(loggedInUser, LocalDate.of(2016, Month.MAY, 2))
									.description("Marianos")
									.category(Category.GROCERIES)
									.amount(BigDecimal.valueOf(115.63).setScale(2, BigDecimal.ROUND_HALF_UP)).build());
					Context.transactionGateway
							.save(new Transaction.TransactionBuilder(loggedInUser, LocalDate.of(2016, Month.MAY, 4))
								.description("Marianos")
								.category(Category.GROCERIES)
								.amount(BigDecimal.valueOf(76.89).setScale(2, BigDecimal.ROUND_HALF_UP)).build());
					Context.transactionGateway
							.save(new Transaction.TransactionBuilder(loggedInUser, LocalDate.of(2016, Month.MAY, 1))
									.category(Category.RENT).description("Marianos")
									.amount(BigDecimal.valueOf(1350).setScale(2, BigDecimal.ROUND_HALF_UP)).build());
					Context.transactionGateway
							.save(new Transaction.TransactionBuilder(loggedInUser, LocalDate.of(2016, Month.MAY, 5))
									.category(Category.AUTO_LOAN).description("Marianos")
									.amount(BigDecimal.valueOf(115.63).setScale(2, BigDecimal.ROUND_HALF_UP)).build());
					Context.transactionGateway
							.save(new Transaction.TransactionBuilder(loggedInUser, LocalDate.of(2016, Month.MAY, 5))
									.category(Category.SERVICE_FEE)
									.amount(BigDecimal.valueOf(25.00).setScale(2, BigDecimal.ROUND_HALF_UP)).build());
					Context.transactionGateway
							.save(new Transaction.TransactionBuilder(loggedInUser, LocalDate.of(2016, Month.MAY, 5))
									.category(Category.FUEL)
									.amount(BigDecimal.valueOf(27.19).setScale(2, BigDecimal.ROUND_HALF_UP)).build());
				}

				@Test
				public void correctBudgetItemsShouldShow() {
					User loggedInUser = Context.gatekeeper.loggedInUser;
					BudgetPresenter presenter = new BudgetPresenter();
					BudgetPresenterUseCase usecase = new BudgetPresenterUseCase();
					Month month = Month.MAY;
					Year year = Year.of(2016);
					usecase.requestBudget(loggedInUser, month, year, presenter);

					List<BudgetItem> budgetItems = Context.budgetItemGateway.findAllForUserAndBudgetPeriod(loggedInUser,
							month, year);

					List<Transaction> transactions = Context.transactionGateway
							.findAllForUserAndBudgetPeriod(loggedInUser, month, year);
					
					
					BudgetPresenterResponseModel responseModel = new BudgetPresenterResponseModel();
					budgetItems.forEach(item -> responseModel.addParentCategory(item.getCategory().parentCategory));
					transactions.forEach(item -> responseModel.addParentCategory(item.getCategory().parentCategory));
					
					budgetItems.forEach(item -> responseModel.addBudgetItem(item));
					transactions.forEach(item -> responseModel.addTransaction(item));
					
					
					
					Set<String> expectedParentCategories = new HashSet<>();
					budgetItems.forEach(item -> expectedParentCategories.add(item.getCategory().parentCategory));
					transactions.forEach(item -> expectedParentCategories.add(item.getCategory().parentCategory));
					
					List<ViewableBudgetItem> expectedBudgetItems = new ArrayList<>();
					budgetItems.forEach(item -> item.calculateActual(transactions));
					budgetItems.forEach(item -> item.calculateWhatsLeft());
					budgetItems.forEach(item -> expectedBudgetItems.add(makeViewable(item)));
					
					Collections.sort(expectedBudgetItems, new Comparator<ViewableBudgetItem>() {
						public int compare(ViewableBudgetItem o1, ViewableBudgetItem o2) {
							return o1.category.compareTo(o2.category);
						}
					});
					
					List<ViewableTransaction> expectedTransactions = new ArrayList<>();
					transactions.forEach(item -> expectedTransactions.add(makeViewable(item)));
					Collections.sort(expectedTransactions, new Comparator<ViewableTransaction>() {
						public int compare(ViewableTransaction o1, ViewableTransaction o2) {
							int date = o1.date.compareTo(o2.date);
							if (date != 0) { return date * -1; }
							int parentCategory = o1.parentCategory.compareTo(o2.parentCategory);
							if (parentCategory != 0) { return parentCategory; }
							int category = o1.category.compareTo(o2.category);
							return category;
						}
					});
					
					presenter.present(responseModel);
					List<ViewableBudgetItem> actualBudgetItems = presenter.viewModel.viewableBudgetItems;
					Set<String> actualParentCategories = presenter.viewModel.viewableParentCategories;
					List<ViewableTransaction> actualTransactions = presenter.viewModel.viewableTransactions;
					
					assertEquals(expectedParentCategories, actualParentCategories);
					assertEquals(expectedBudgetItems, actualBudgetItems);
					assertEquals(expectedTransactions, actualTransactions);
					
				}

				private ViewableTransaction makeViewable(Transaction transaction) {
					ViewableTransaction viewableTransaction = new ViewableTransaction();
					viewableTransaction.id = transaction.getId();
					viewableTransaction.user = transaction.getUser().getId();
					viewableTransaction.date = transaction.getDate().format(dateFormatter);
					viewableTransaction.amount = transaction.getAmount().toString();
					viewableTransaction.description = transaction.getDescription();
					viewableTransaction.parentCategory = transaction.getCategory().parentCategory;
					viewableTransaction.category = transaction.getCategory().category;
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

		}

	}
}
