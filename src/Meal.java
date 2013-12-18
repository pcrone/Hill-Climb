import java.util.ArrayList;

public class Meal {
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
	
	public boolean removeFood(int index) {
		int[] tempNutrition = totalNutrition.clone();
		
		for (int i = 0; i < tempNutrition.length; i++) {
			tempNutrition[i] -= meal.get(index).dailyNutrition[i];
			
			if (tempNutrition[i] < 95)
				return false;
		}
		
		totalNutrition = tempNutrition;
		totalCost -= meal.get(index).cost;
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

	@Override
	public Meal clone() {
		Meal item = new Meal();
		
		for (int i = 0; i < meal.size(); i++) {
			item.meal.add(meal.get(i).clone());
		}
		item.totalCost = totalCost;
		item.totalNutrition = totalNutrition.clone();
		
		return item;
	}	
}
