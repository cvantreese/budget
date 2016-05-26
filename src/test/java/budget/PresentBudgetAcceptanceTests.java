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
import java.util.LinkedHashMap;
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
					Collections.sort(budgetItems, new Comparator<BudgetItem>() {
						public int compare(BudgetItem o1, BudgetItem o2) {
							int first = o1.getCategory().parentCategory.compareTo(o2.getCategory().parentCategory);
							int second = o1.getCategory().category.compareTo(o2.getCategory().category);
							return (first == 0) ? second : first;
						}
					});
					
					List<Transaction> transactions = Context.transactionGateway
							.findAllForUserAndBudgetPeriod(loggedInUser, month, year);
					Collections.sort(transactions, new Comparator<Transaction>() {
						public int compare(Transaction o1, Transaction o2) {
							return o1.getDate().compareTo(o2.getDate());
						}
					});
					
					Set<Category> uniqueCategories = new HashSet<>();
					budgetItems.forEach(item -> uniqueCategories.add(item.getCategory()));
					transactions.forEach(item -> uniqueCategories.add(item.getCategory()));
					
					List<Category> uniqueCategoryList = new ArrayList<Category>();
					uniqueCategories.forEach(item -> uniqueCategoryList.add(item));
					Collections.sort(uniqueCategoryList, new Comparator<Category>() {
						public int compare(Category o1, Category o2) {
							return o1.category.compareTo(o2.category);
						}
					});
					
					Map<Category, BudgetItem> budgetItemsMapByCategory = new LinkedHashMap<>();
					uniqueCategoryList.forEach(item -> budgetItemsMapByCategory.put(item, null));
					
					for (BudgetItem budgetItem : budgetItems) {
						budgetItemsMapByCategory.put(budgetItem.getCategory(), makeViewable(budgetItem));
					}
					
					for (Category category : uniqueCategories) {
						if (budgetItemsMapByCategory.get(category) == null) {
							budgetItemsMapByCategory.put(category, makeViewable(new BudgetItem.BudgetItemBuilder(loggedInUser, month, year, category).build()));
						}
					}
					
					/*
					Map<Category, List<TransactionViewModel>> transactionMapByCategory = new HashMap<>();
					uniqueCategoryList.forEach(item -> transactionMapByCategory.put(item, new ArrayList<>()));
					
					for (Transaction transaction : transactions) {
						transactionMapByCategory.get(transaction.getCategory()).add(makeViewable(transaction));
					}
					
					Map<Category, BudgetItemViewModel> budgetItemsMapByCategory = new HashMap<>();
					uniqueCategoryList.forEach(item -> budgetItemsMapByCategory.put(item, null));
					
					for (BudgetItem budgetItem : budgetItems) {
						budgetItemsMapByCategory.put(budgetItem.getCategory(), makeViewable(budgetItem));
					}
					
					for (Category category : uniqueCategories) {
						if (budgetItemsMapByCategory.get(category) == null) {
							budgetItemsMapByCategory.put(category, makeViewable(new BudgetItem.BudgetItemBuilder(loggedInUser, month, year, category).build()));
						}
					}
					Map<BudgetItemViewModel, List<TransactionViewModel>> budgetAndTransactionsMap = new LinkedHashMap<>();
					uniqueCategoryList.forEach(item -> budgetAndTransactionsMap.put(budgetItemsMapByCategory.get(item), transactionMapByCategory.get(item)));
					
					Map<Category, Map<BudgetItemViewModel, List<TransactionViewModel>>> budgetCategoryMap = new LinkedHashMap<>();
										
					for (BudgetItem budgetItem : budgetAndTransactionsMap.keySet()) {
						Map<BudgetItemViewModel, List<TransactionViewModel>> localBudgetAndTransactionsMap = new LinkedHashMap<>();
						localBudgetAndTransactionsMap.put(makeViewablebudgetItem, budgetAndTransactionsMap.get(budgetItem));
						budgetCategoryMap.put(budgetItem.getCategory(), localBudgetAndTransactionsMap);
					}
					
					Map<String, Map<Category, Map<BudgetItemViewModel, List<TransactionViewModel>>>> budgetMap = new LinkedHashMap<>();
					for (Category category : uniqueCategoryList) {
						String key = category.parentCategory;
						Map<Category, Map<BudgetItemViewModel, List<TransactionViewModel>>> value = new LinkedHashMap<>();
						
						value.put(category, budgetCategoryMap.get(category));
						budgetMap.put(key, value);
					}
					*/
					BudgetViewModel budgetViewModel = new BudgetViewModel();
					for (Category category : uniqueCategoryList) {
						Map<String, Map<Category, Map<BudgetItemViewModel, List<TransactionViewModel>>>> budgetMap = new LinkedHashMap<>();
						
						Map<Category, Map<BudgetItemViewModel, List<TransactionViewModel>>> budgetCategoryMap = new LinkedHashMap<>();
						budgetCategoryMap.put(category, null);
						budgetMap.put(category, budgetCategoryMap);
						
						Map<BudgetItemViewModel, List<TransactionViewModel>> budgetAndTransactionMap = new LinkedHashMap<>();
						BudgetItem filteredBudgetItem = budgetItemsMapByCategory.get(category);
						BudgetItemViewModel budgetItemViewModel = makeViewable(filteredBudgetItem);
						
						budgetAndTransactionMap.put(budgetItemViewModel, null);
						
						//TODO finish this -in middle of simplifying code
					}
					
					uniqueCategoryList.forEach(item -> System.out.println("key: " + item + " :: value: " + budgetMap.get(item.parentCategory)));
					//budgetCategoryMap.keySet().forEach(item -> System.out.println(item));
					
					//budgetItemsMapByCategory.keySet().forEach(item -> System.out.println(item));
					//transactionMapByCategory.get(Category.GROCERIES).forEach(item -> System.out.println(item));
					//System.out.println(budgetItemsMapByCategory.get(Category.RENT));
					
					
					BudgetPresenterResponseModel responseModel = new BudgetPresenterResponseModel();

					// Map<BudgetItem, Transaction> budgetTransactionMap = new
					// HashMap

					// sortedCategoryList.forEach(item ->
					// responseModel.addResponseModel(item));

				}

			}

		}

	}
}
