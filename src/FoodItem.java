public class FoodItem {
	String name = "";
	int[] dailyNutrition = {0,0,0,0,0};
	int cost = 0;
	
	public FoodItem() {};
	
	public FoodItem(String name,
			int carb, 
			int protein, 
			int fat, 
			int vitamin, 
			int mineral, 
			int cost) {
		
		this.name = name;
		dailyNutrition = new int[]{carb, protein, fat, vitamin, mineral};
		this.cost = cost;			
	}

	public FoodItem clone() {
		FoodItem item = new FoodItem();
		
		item.name = name;
		item.dailyNutrition = dailyNutrition.clone();
		item.cost = cost;
		
		return item;
	}			
}
