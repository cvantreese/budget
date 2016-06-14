package budget.usecases.budgetpresenter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import budget.entities.BudgetItem.Type;
import budget.usecases.budgetpresenter.BudgetPresenterViewModel.ViewableBudgetItem;
import budget.usecases.budgetpresenter.BudgetPresenterViewModel.ViewableTransaction;

public class BudgetPresenterViewModel {

	public List<ViewableBudgetItem> viewableBudgetItems = new ArrayList<>();
	public Set<String> viewableParentCategories = new TreeSet<>();
	public List<ViewableTransaction> viewableTransactions = new ArrayList<>();

	public void addParentCategory(String parentCategory) {
		viewableParentCategories.add(parentCategory);
	}
	
	public void addViewableTransaction(ViewableTransaction viewableTransaction) {
		viewableTransactions.add(viewableTransaction);
		Collections.sort(viewableTransactions, new Comparator<ViewableTransaction>() {
			public int compare(ViewableTransaction o1, ViewableTransaction o2) {
				int date = o1.date.compareTo(o2.date);
				if (date != 0) { return date * -1; }
				int parentCategory = o1.parentCategory.compareTo(o2.parentCategory);
				if (parentCategory != 0) { return parentCategory; }
				int category = o1.category.compareTo(o2.category);
				return category;
			}
		});
	}
	
	public void addViewableBudgetItem(ViewableBudgetItem viewableBudgetItem) {
		viewableBudgetItems.add(viewableBudgetItem);
		Collections.sort(viewableBudgetItems, new Comparator<ViewableBudgetItem>() {
			public int compare(ViewableBudgetItem o1, ViewableBudgetItem o2) {
				return o1.category.compareTo(o2.category);
			}
		});
	}
	
	public static class ViewableTransaction {

		public String id;
		public String user;
		public String date;
		public String amount;
		public String description;
		public String parentCategory;
		public String category;
		
		
		
		@Override
		public String toString() {
			return "ViewableTransaction [id=" + id + ", user=" + user + ", date=" + date + ", amount=" + amount
					+ ", description=" + description + ", parentCategory=" + parentCategory + ", category=" + category
					+ "]";
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((amount == null) ? 0 : amount.hashCode());
			result = prime * result + ((category == null) ? 0 : category.hashCode());
			result = prime * result + ((date == null) ? 0 : date.hashCode());
			result = prime * result + ((description == null) ? 0 : description.hashCode());
			result = prime * result + ((id == null) ? 0 : id.hashCode());
			result = prime * result + ((parentCategory == null) ? 0 : parentCategory.hashCode());
			result = prime * result + ((user == null) ? 0 : user.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ViewableTransaction other = (ViewableTransaction) obj;
			if (amount == null) {
				if (other.amount != null)
					return false;
			} else if (!amount.equals(other.amount))
				return false;
			if (category == null) {
				if (other.category != null)
					return false;
			} else if (!category.equals(other.category))
				return false;
			if (date == null) {
				if (other.date != null)
					return false;
			} else if (!date.equals(other.date))
				return false;
			if (description == null) {
				if (other.description != null)
					return false;
			} else if (!description.equals(other.description))
				return false;
			if (id == null) {
				if (other.id != null)
					return false;
			} else if (!id.equals(other.id))
				return false;
			if (parentCategory == null) {
				if (other.parentCategory != null)
					return false;
			} else if (!parentCategory.equals(other.parentCategory))
				return false;
			if (user == null) {
				if (other.user != null)
					return false;
			} else if (!user.equals(other.user))
				return false;
			return true;
		}
		
		
		
	}

	public static class ViewableBudgetItem {

		public String id;
		public String category;
		public String budgeted;
		public String type;
		public String actual;
		public String whatsLeft;
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((actual == null) ? 0 : actual.hashCode());
			result = prime * result + ((budgeted == null) ? 0 : budgeted.hashCode());
			result = prime * result + ((category == null) ? 0 : category.hashCode());
			result = prime * result + ((id == null) ? 0 : id.hashCode());
			result = prime * result + ((type == null) ? 0 : type.hashCode());
			result = prime * result + ((whatsLeft == null) ? 0 : whatsLeft.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ViewableBudgetItem other = (ViewableBudgetItem) obj;
			if (actual == null) {
				if (other.actual != null)
					return false;
			} else if (!actual.equals(other.actual))
				return false;
			if (budgeted == null) {
				if (other.budgeted != null)
					return false;
			} else if (!budgeted.equals(other.budgeted))
				return false;
			if (category == null) {
				if (other.category != null)
					return false;
			} else if (!category.equals(other.category))
				return false;
			if (id == null) {
				if (other.id != null)
					return false;
			} else if (!id.equals(other.id))
				return false;
			if (type == null) {
				if (other.type != null)
					return false;
			} else if (!type.equals(other.type))
				return false;
			if (whatsLeft == null) {
				if (other.whatsLeft != null)
					return false;
			} else if (!whatsLeft.equals(other.whatsLeft))
				return false;
			return true;
		}
		

	}

}
