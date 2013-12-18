public class HillClimb {
	public Meal finalMeal;

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
			tempNode = currentNode.clone();
			
			tempNode.meal.remove(i);
			if (tempNode.totalCost < minCost) {
				nextNode = tempNode.clone();
				minCost = tempNode.totalCost;
			}
			
			tempNode = currentNode.clone();
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
		
		for (int i = 0; i < 5000; i++) {
			tempNode = runHillClimb();
			
			if (tempNode.totalCost < maxNode.totalCost)
				maxNode = tempNode;
		}
		
		return maxNode;
	}
	
	public static void main(String[] args) {		
		HillClimb climb = new HillClimb();
/*
		climb.finalMeal = climb.runRepeatHillClimb();
		
		// Output Initial Random Node
		System.out.println(climb.finalMeal.meal.size() + "\n" + climb.finalMeal.totalCost);
		for (int i = 0; i < climb.finalMeal.totalNutrition.length; i++)
			System.out.print(climb.finalMeal.totalNutrition[i] + " ");
*/
		SimulatedAnnealing sim = new SimulatedAnnealing();
		climb.finalMeal = sim.runSimulatedAnnealing();
		
		// Output Initial Random Node
		System.out.println(climb.finalMeal.meal.size() + "\n" + climb.finalMeal.totalCost);
		for (int i = 0; i < climb.finalMeal.totalNutrition.length; i++)
			System.out.print(climb.finalMeal.totalNutrition[i] + " ");
	
	}
	
}
