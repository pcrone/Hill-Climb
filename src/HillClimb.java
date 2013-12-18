public class HillClimb {
	public Meal finalMeal;
	public int totalCandidates = 0;
	private boolean useHeuristic;
	
	public HillClimb(boolean useHeuristic) {
		this.useHeuristic = useHeuristic;
	}
	
	public Meal findInitialMeal() {
		FoodMenu menu = new FoodMenu();
		Meal initialNode = new Meal();
		boolean isInitialState = false;
		
		while (!isInitialState) {
			initialNode.addFood(menu.getRandomItem());
			
			if (initialNode.greaterThan100())
				initialNode = new Meal();		
			
			if (initialNode.withinRange())
				isInitialState = true;
		}
		
		return initialNode;
	}
	
	public Meal evaluate(Meal currentNode) {
		Meal tempNode = currentNode.clone(); 
		Meal nextNode = currentNode.clone();
		double minH = calculateHeuristic(currentNode);
		double nextH;
		
		for (int i = 0; i < tempNode.meal.size(); i++) {
			// Remove a food item
			tempNode.removeFood(i);
			
			// Calculate the new node's h value
			nextH = calculateHeuristic(tempNode);
			
			if (!tempNode.lessThan95())
				totalCandidates++;
			
			// If the changed node's h value is less than the current
			// and still in range, save it
			if (nextH < minH && !tempNode.lessThan95()) {
				nextNode = tempNode.clone();
				minH = nextH;
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
		
		for (int i = 0; i < 1000; i++) {
			tempNode = runHillClimb();
			
			if (tempNode.totalCost < maxNode.totalCost)
				maxNode = tempNode;
		}
		
		return maxNode;
	}
	
	private double calculateHeuristic(Meal currentNode) {
		double current = 0;
		
		// If false, find minimum cost
		if (!useHeuristic) {
			current = currentNode.totalCost;
		}
		// else, find "best" dish: minimum cost + nutrition diviation
		else {
			for (int i = 0; i < currentNode.totalNutrition.length; i++) {
				current += Math.abs(100 - currentNode.totalNutrition[i]);
			}
			current += currentNode.totalCost;
		}
		
		return current;
	}
	
	public static void main(String[] args) {		
		HillClimb climb = new HillClimb(false);
		System.out.println("Running Hill Climb....");
		climb.finalMeal = climb.runRepeatHillClimb();
				
		// Output Final Node from Hill Climb
		System.out.println("Random-Restart Hill Climbing:");
		System.out.println("Number of Items: " + climb.finalMeal.meal.size());
		System.out.println("Total Cost: " +	climb.finalMeal.totalCost);
		System.out.print("Total Nutrition: ");
		for (int i = 0; i < climb.finalMeal.totalNutrition.length; i++)
			System.out.print(climb.finalMeal.totalNutrition[i] + " ");
		System.out.println("\nTotal Candidates: " + climb.totalCandidates);

		SimulatedAnnealing sim = new SimulatedAnnealing(false);
		System.out.println("\nRunning Simulated Annealing....");
		climb.finalMeal = sim.runSimulatedAnnealing();
		
		// Output Final Node from Simulated Anneal
		System.out.println("Simulated Annealing:");
		System.out.println("Number of Items: " + climb.finalMeal.meal.size());
		System.out.println("Total Cost: " +	climb.finalMeal.totalCost);
		System.out.print("Total Nutrition: ");
		for (int i = 0; i < climb.finalMeal.totalNutrition.length; i++)
			System.out.print(climb.finalMeal.totalNutrition[i] + " ");
		System.out.println("\nTotal Candidates: " + sim.totalCandidates);
		
		
		// Run with heuristic function
		climb = new HillClimb(true);
		System.out.println("\n\n\nRunning Hill Climb with Heuristic....");
		climb.finalMeal = climb.runRepeatHillClimb();
				
		// Output Final Node from Hill Climb
		System.out.println("Random-Restart Hill Climbing:");
		System.out.println("Number of Items: " + climb.finalMeal.meal.size());
		System.out.println("Total Cost: " +	climb.finalMeal.totalCost);
		System.out.print("Total Nutrition: ");
		for (int i = 0; i < climb.finalMeal.totalNutrition.length; i++)
			System.out.print(climb.finalMeal.totalNutrition[i] + " ");
		System.out.println("\nTotal Candidates: " + climb.totalCandidates);

		sim = new SimulatedAnnealing(true);
		System.out.println("\nRunning Simulated Annealing with Heuristic....");
		climb.finalMeal = sim.runSimulatedAnnealing();
		
		// Output Final Node from Simulated Anneal
		System.out.println("Simulated Annealing:");
		System.out.println("Number of Items: " + climb.finalMeal.meal.size());
		System.out.println("Total Cost: " +	climb.finalMeal.totalCost);
		System.out.print("Total Nutrition: ");
		for (int i = 0; i < climb.finalMeal.totalNutrition.length; i++)
			System.out.print(climb.finalMeal.totalNutrition[i] + " ");
		System.out.println("\nTotal Candidates: " + sim.totalCandidates);
	}
	
}
