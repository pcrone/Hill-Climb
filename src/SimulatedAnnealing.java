import java.util.ArrayList;

public class SimulatedAnnealing {
	private final int STARTING_TEMP = 15;
	private final double DELTA_T = 0.95;
	
	private double temperature;
	ArrayList<Meal> explored = new ArrayList<Meal>();
	
	private Meal findInitialMeal() {
		FoodMenu menu = new FoodMenu();
		Meal initialNode = new Meal();
		
		boolean isInitialState = false;
		
		while (!isInitialState) {
			initialNode.addFood(menu.getRandomItem());
			
			// If outside range, start over
			if (initialNode.greaterThan100())
				initialNode = new Meal();		
			
			if (initialNode.withinRange())
				isInitialState = true;
		}
		
		return initialNode;
	}
	
	private Meal findRandomNode(Meal meal) {
		FoodMenu menu = new FoodMenu();
		Meal tempMeal = meal.clone();
		
		FoodItem randomItem;
		int randomChange;
		int randomIndex;
		
		while (tempMeal.lessThan95() || explored.contains(tempMeal)) {
			tempMeal = meal.clone();
			randomChange = (int)(Math.random()*2);
			
			switch (randomChange) {
				// Add a random food item
				case 0: 
					randomItem = menu.menu.get((int)(Math.random()*menu.menu.size()) );
					tempMeal.addFood(randomItem);
					break;
				// Remove a random food item
				case 1:
					randomIndex = (int)(Math.random()*tempMeal.meal.size());
					tempMeal.removeFood(randomIndex);
			}
		}
		
		return tempMeal;
	}
	
	public Meal runSimulatedAnnealing() {
		Meal currentNode = findInitialMeal();
		Meal nextNode;
		temperature = STARTING_TEMP;
		int delta_e;
		double probability;
		explored.add(currentNode);
		
		while (true) {
			temperature -= 0.001;
			if (temperature <= 0)
				return currentNode;
//			System.out.println(temperature);

			if (!explored.contains(currentNode))
				explored.add(currentNode);
			
			nextNode = findRandomNode(currentNode);
			
			delta_e = currentNode.totalCost - nextNode.totalCost;
			
			if (delta_e > 0) {
				currentNode = nextNode;
//				System.out.println("uphill" + " = " + currentNode.totalCost);
			}
			else {
				probability = Math.exp(delta_e/temperature);
				if (Math.random() < probability) {
					currentNode = nextNode;
//					System.out.println("downhill" + " = " + currentNode.totalCost);
				}
			}
		}
	}
}
