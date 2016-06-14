package budget;

import budget.gateways.BudgetItemGateway;
import budget.gateways.TransactionGateway;
import budget.gateways.UserGateway;

public class Context {

	public interface transactionGateway {

	}
	public static UserGateway userGateway;
	public static Gatekeeper gatekeeper;
	public static BudgetItemGateway budgetItemGateway;
	public static TransactionGateway transactionGateway;

}
