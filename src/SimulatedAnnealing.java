import java.util.ArrayList;

public class SimulatedAnnealing {
	private int startingTemp;
	private double delta_t;
	private boolean useHeuristic;
	
	ArrayList<Meal> explored = new ArrayList<Meal>();
	
	public int totalCandidates = 0;
	
	public SimulatedAnnealing(boolean useHeuristic) {
		this.useHeuristic = useHeuristic;
		
		if (useHeuristic) {
			startingTemp = 50;
			delta_t = 0.008;
		}
		else {
			startingTemp = 15;
			delta_t = 0.001;
		}
	}
	
	private Meal findInitialMeal() {
		FoodMenu menu = new FoodMenu();
		Meal initialNode = new Meal();
		
		boolean isInitialState = false;
		
		while (!isInitialState) {
			// Add a random item to the state
			initialNode.addFood(menu.getRandomItem());
			
			// If all elements outside range, start over
			if (initialNode.greaterThan100())
				initialNode = new Meal();		
			
			// If all elements within range, keep state
			if (initialNode.withinRange())
				isInitialState = true;
		}
		
		return initialNode;
	}
	
	private Meal findRandomNode(Meal meal) {
		ArrayList<Meal> neighbors = new ArrayList<Meal>();
		FoodMenu menu = new FoodMenu();
		Meal tempMeal = meal.clone();
		
		FoodItem randomItem;
		int randomChange;
		int randomIndex;
		
		for (int i = 0; i < menu.menu.size(); i++) {
			// Add food item to current state
			randomItem = menu.menu.get(i);
			tempMeal.addFood(randomItem);
			
			// If it's legal, add it to the list of neighbors
			if (!tempMeal.lessThan95() && !explored.contains(tempMeal))
				neighbors.add(tempMeal);
			
			tempMeal = meal.clone();
		}
		
		for (int i = 0; i < tempMeal.meal.size(); i++) {
			tempMeal.removeFood(i);
			
			// If it's legal, add it to the list of neighbors
			if (!tempMeal.lessThan95() && !explored.contains(tempMeal))
				neighbors.add(tempMeal);
			
			tempMeal = meal.clone();
		}
		
		/*
		while (tempMeal.lessThan95() || explored.contains(tempMeal)) {
			tempMeal = meal.clone();
			// Randomly pick whether to add or remove a food item
			randomChange = (int)(Math.random()*2);
			
			switch (randomChange) {
				// Add a random food itemm
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
		*/
		
		totalCandidates += neighbors.size();
		tempMeal = neighbors.get((int)(Math.random()*neighbors.size()) );
		
		return tempMeal;
	}
	
	public Meal runSimulatedAnnealing() {
		Meal currentNode = findInitialMeal();
		Meal nextNode;
		double delta_e;
		int time = 0;
		double temperature;
		double probability;
		explored.add(currentNode);
		
		while (true) {
			// Update the temperature
			temperature = updateSchedule(time);
			if (temperature <= 0)
				return currentNode;

			// Update time
			time++;
			
			// Add the current node if not in explored nodes
			if (!explored.contains(currentNode))
				explored.add(currentNode);
			
			// Get a random neighbor of the current node
			nextNode = findRandomNode(currentNode);
			
			// Find the change in energy
			if (!useHeuristic)
				delta_e = calculateMinCost(currentNode, nextNode);
			else
				delta_e = calculateHeuristic(currentNode, nextNode);
			
			// If the change is good, go uphill
			if (delta_e > 0) {
				currentNode = nextNode;
//				totalCandidates++;
			}
			// If the change is bad...
			else {
				// Calculate the probability of going to the next node
				probability = Math.exp(delta_e/temperature);
				
				// If in the probability range, go downhill
				if (Math.random() <= probability) {
					currentNode = nextNode;
//					totalCandidates++;
				}
			}
		}
	}
	
	private double updateSchedule(int time) {
		return startingTemp - (time * delta_t);
	}
	
	private double calculateMinCost(Meal currentNode, Meal nextNode) {
		return currentNode.totalCost - nextNode.totalCost;
	}
	
	private double calculateHeuristic(Meal currentNode, Meal nextNode) {
		// Calculate H for the current Node
		double current = 0;
		for (int i = 0; i < currentNode.totalNutrition.length; i++) {
			current += Math.abs(100 - currentNode.totalNutrition[i]);
		}
		current += currentNode.totalCost;
		
		// Calculate H for the next Node
		double next = 0;
		for (int i = 0; i < nextNode.totalNutrition.length; i++) {
			next += Math.abs(100 - nextNode.totalNutrition[i]);
		}
		next += nextNode.totalCost;
		
		return current - next;
	}
}
