import java.util.*;

public class SimulatedAnnealing {
	private final int STARTING_TEMP = 100;
	private final double DELTA_T = 0.007;
	
	private double temperature;
	
	private Meal findInitialMeal() {
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
	
	private Meal findRandomNode(Meal meal) {
		FoodMenu menu = new FoodMenu();
		Meal tempMeal = meal.clone();
		
		ArrayList<Meal> nextMeals = new ArrayList<Meal>();
		for (int i = 0; i < tempMeal.meal.size(); i++) {
				for (int j = 0; j < menu.menu.size(); j++) {
					if (tempMeal.replaceFood(menu.menu.get(j), i) && (tempMeal.totalCost - meal.totalCost) != 0)
						nextMeals.add(tempMeal);
					
					tempMeal = meal.clone();
				}
				
				if (tempMeal.removeFood(i) && (tempMeal.totalCost - meal.totalCost) != 0)
					nextMeals.add(tempMeal);
				
				tempMeal = meal.clone();
		}
		
		for (int i = 0; i < menu.menu.size(); i++) {
			if (tempMeal.addFood(menu.menu.get(i)) && (tempMeal.totalCost - meal.totalCost) != 0)
				nextMeals.add(tempMeal);
			
			tempMeal = meal.clone();
		}
		
		int randomIndex = (int)(Math.random()*(nextMeals.size()-1));
		tempMeal = nextMeals.get(randomIndex);
		
		return tempMeal;
	}
	
	public Meal runSimulatedAnnealing() {
		Meal currentNode = findInitialMeal();
		Meal nextNode;
		temperature = STARTING_TEMP;
		int delta_e;
		double probability;
		int time = 0;
		
		while (temperature > DELTA_T) {
		//	if (time % 75 == 0) {
				temperature -= DELTA_T;
				System.out.println(temperature);
		//	}	
			time++;
			
			nextNode = findRandomNode(currentNode);
			
			delta_e = currentNode.totalCost - nextNode.totalCost;
			
			if (delta_e > 0)
				currentNode = nextNode;
			else {
				probability = Math.exp(delta_e/temperature);
				if (Math.random() < probability)
					currentNode = nextNode;
			}
		}
		return currentNode;
	}
}
