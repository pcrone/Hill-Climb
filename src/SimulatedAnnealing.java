public class SimulatedAnnealing {
	private final int STARTING_TEMP = 20;
	private final double DELTA_T = 0.95;
	
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
		
		FoodItem randomItem;
		int randomChange;
		int randomIndex;
		
		while (tempMeal.totalCost == meal.totalCost) {
			tempMeal = meal.clone();
			randomItem = menu.menu.get((int)(Math.random()*menu.menu.size()) );
			randomIndex = (int)(Math.random()*tempMeal.meal.size());
			randomChange = (int)(Math.random()*3);
			
			switch (randomChange) {
				case 0: tempMeal.replaceFood(randomItem, randomIndex);
					break;
				case 1: tempMeal.addFood(randomItem);
					break;
				case 2: tempMeal.removeFood(randomIndex);
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
		
		while (temperature > 0) {
			temperature *= DELTA_T;
			System.out.println(temperature);
			
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
		return currentNode;
	}
}
