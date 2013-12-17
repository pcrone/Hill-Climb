import java.util.*;

public class HillClimb {
	
	public Meal initialMeal;
	
	private class FoodItem {
		int[] dailyNutrition = {0,0,0,0,0};
		int cost = 0;
		
		public FoodItem() {};
		
		public FoodItem(int carb, 
				int protein, 
				int fat, 
				int vitamin, 
				int mineral, 
				int cost) {
			
			dailyNutrition = new int[]{carb, protein, fat, vitamin, mineral};
			this.cost = cost;			
		}

		public FoodItem clone() {
			FoodItem item = new FoodItem();
			
			item.dailyNutrition = dailyNutrition.clone();
			item.cost = cost;
			
			return item;
		}		
	}
	
	private class FoodMenu {
		ArrayList<FoodItem> menu = new ArrayList<FoodItem>();
		
		public FoodMenu() {
			menu.add(new FoodItem(1,3,4,5,6,12));	// F1
			menu.add(new FoodItem(2,4,3,2,5,12));	// F2
			menu.add(new FoodItem(2,1,5,2,4,9));	// F3
			menu.add(new FoodItem(2,1,5,2,4,9));	// F4
			menu.add(new FoodItem(6,1,2,2,3,5));	// F5
			menu.add(new FoodItem(1,6,1,4,7,8));	// F6
			menu.add(new FoodItem(4,2,2,6,1,9));	// F7
			menu.add(new FoodItem(1,5,3,2,5,12));	// F8
			menu.add(new FoodItem(3,2,3,6,2,9));	// F9
			menu.add(new FoodItem(7,1,3,2,3,7));	// F10
			menu.add(new FoodItem(2,6,1,4,1,8));	// F11
			menu.add(new FoodItem(2,1,6,2,3,11));	// F12
			menu.add(new FoodItem(2,5,1,2,7,10));	// F13
			menu.add(new FoodItem(6,2,2,1,2,8));	// F14
			menu.add(new FoodItem(3,1,1,3,6,9));	// F15
			menu.add(new FoodItem(5,5,4,2,1,15));	// F16
			menu.add(new FoodItem(2,4,3,1,2,14));	// F17
			menu.add(new FoodItem(5,6,1,4,0,8));	// F18
			menu.add(new FoodItem(3,2,3,6,2,9));	// F19
			menu.add(new FoodItem(2,6,1,4,1,8));	// F20
		}
		
		public FoodItem getRandomItem() {
			return menu.get((int)(Math.random() * 20));
		}
	}
	
	private class Meal {
		ArrayList<FoodItem> meal = new ArrayList<FoodItem>();
		int totalCost = 0;
		int[] totalNutrition = {0,0,0,0,0};

		public boolean addFood(FoodItem item) {
			int[] tempNutrition = totalNutrition.clone();
			
			for (int i = 0; i < tempNutrition.length; i++) {
				if (tempNutrition[i] + item.dailyNutrition[i] > 100)
					return false;
				
				tempNutrition[i] += item.dailyNutrition[i];
			}
			
			totalNutrition = tempNutrition;
			totalCost += item.cost;
			meal.add(item);
			
			return true;
		}
		
		public boolean replaceFood(FoodItem item, int index) {
			int[] tempTotalNutrition = totalNutrition.clone();
			
			FoodItem tempItem = meal.get(index);
			
			for (int i = 0; i < tempTotalNutrition.length; i++) {
				tempTotalNutrition[i] -= tempItem.dailyNutrition[i];
				tempTotalNutrition[i] += item.dailyNutrition[i];
				
				if (tempTotalNutrition[i] < 95 || tempTotalNutrition[i] > 100)
					return false;
			}
			
			totalNutrition = tempTotalNutrition;
			totalCost -= tempItem.cost;
			totalCost += item.cost;
			meal.set(index, item);
			
			return true;
		}
		
		public boolean withinRange() {
			for (int i = 0; i < totalNutrition.length; i++)
				if (totalNutrition[i] < 95 || totalNutrition[i] > 100)
					return false;
			return true;
		}

		@Override
		public Meal clone() {
			Meal item = new Meal();
			
			for (int i = 0; i < meal.size(); i++) {
				item.meal.add(meal.get(i));
			}
			item.totalCost = totalCost;
			item.totalNutrition = totalNutrition.clone();
			
			return item;
		}	
	}
	
	public Meal findInitialMeal() {
		FoodMenu menu = new FoodMenu();
		Meal initialNode = new Meal();
		
		boolean isInitialState = false;
		
		while (!isInitialState) {
			if (!initialNode.addFood(menu.getRandomItem()))
				initialNode = new Meal();		
			
			if (initialNode.withinRange())
				isInitialState = true;
		}
		
		return initialNode;
	}

	public Meal evaluate(Meal currentNode) {
		FoodMenu menu = new FoodMenu();
		Meal tempNode = currentNode.clone(); 
		Meal nextNode = currentNode.clone();
		int minCost = currentNode.totalCost;
		
		for (int i = 0; i < tempNode.meal.size(); i++) {
			for (int j = 0; j < menu.menu.size(); j++) {
				if (tempNode.replaceFood(menu.menu.get(j), i)
						&& tempNode.totalCost < minCost) {
					nextNode = tempNode.clone();
					minCost = tempNode.totalCost;
				}
			}
			tempNode = currentNode;
		}
		
		return nextNode;
	}
	
	public Meal runHillClimb() {
		Meal currentNode = findInitialMeal();
			
		int currentCost;
		int nextCost;
		
		do {
			currentCost = currentNode.totalCost;
			
			// Get next node
			currentNode = evaluate(currentNode);
			
			nextCost = currentNode.totalCost;
		} while (currentCost > nextCost);
		
		return currentNode;
	}
	
	public Meal runRepeatHillClimb() {
		Meal maxNode;
		Meal tempNode;
		
		maxNode = runHillClimb();
		
		System.out.println(maxNode.meal.size() + "\n" + maxNode.totalCost);
		for (int i = 0; i < maxNode.totalNutrition.length; i++)
			System.out.print(maxNode.totalNutrition[i] + " ");
		System.out.println();
		
		for (int i = 0; i < 20000; i++) {
			tempNode = runHillClimb();
			
			if (tempNode.totalCost < maxNode.totalCost)
				maxNode = tempNode;
		}
		
		return maxNode;
	}
	
	public static void main(String[] args) {		
		HillClimb climb = new HillClimb();
		
		climb.initialMeal = climb.runRepeatHillClimb();
		
		// Output Initial Random Node
		System.out.println(climb.initialMeal.meal.size() + "\n" + climb.initialMeal.totalCost);
		for (int i = 0; i < climb.initialMeal.totalNutrition.length; i++)
			System.out.print(climb.initialMeal.totalNutrition[i] + " ");
	}
	
}
