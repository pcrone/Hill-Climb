import java.util.ArrayList;

public class Meal {
	ArrayList<FoodItem> meal = new ArrayList<FoodItem>();
	int[] state = new int[20];
	int totalCost = 0;
	int[] totalNutrition = {0,0,0,0,0};
	
	public boolean addFood(FoodItem item) {
		int[] tempNutrition = totalNutrition.clone();
		
		for (int i = 0; i < tempNutrition.length; i++)
			tempNutrition[i] += item.dailyNutrition[i];

		totalNutrition = tempNutrition;
		totalCost += item.cost;
		state[item.itemNumber-1]++;
		meal.add(item);
		
		return true;
	}
	
	public boolean removeFood(int index) {
		int[] tempNutrition = totalNutrition.clone();
		
		for (int i = 0; i < tempNutrition.length; i++)
			tempNutrition[i] -= meal.get(index).dailyNutrition[i];
		
		totalNutrition = tempNutrition;
		totalCost -= meal.get(index).cost;
		state[meal.get(index).itemNumber-1]--;
		meal.remove(index);
		
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
	
	public boolean greaterThan100() {
		for (int i = 0; i < totalNutrition.length; i++)
			if (totalNutrition[i] < 100)
				return false;
		return true;
	}
	
	public boolean lessThan95() {
		for (int i = 0; i < totalNutrition.length; i++)
			if (totalNutrition[i] < 95)
				return true;
		return false;
	}

	@Override
	public Meal clone() {
		Meal item = new Meal();
		
		for (int i = 0; i < meal.size(); i++) {
			item.meal.add(meal.get(i).clone());
		}
		item.totalCost = totalCost;
		item.state = state.clone();
		item.totalNutrition = totalNutrition.clone();
		
		return item;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj.getClass() == Meal.class) {
			for (int i = 0; i < state.length; i++) {
				if (state[i] != ((Meal)obj).state[i])
					return false;
			}
			return true;
		}
		return super.equals(obj);
	}
	
	
}
