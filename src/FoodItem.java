public class FoodItem {
	int itemNumber;
	int[] dailyNutrition = {0,0,0,0,0};
	int cost = 0;
	
	public FoodItem() {};
	
	public FoodItem(int itemNumber,
			int carb, 
			int protein, 
			int fat, 
			int vitamin, 
			int mineral, 
			int cost) {
		
		this.itemNumber = itemNumber;
		dailyNutrition = new int[]{carb, protein, fat, vitamin, mineral};
		this.cost = cost;			
	}

	public FoodItem clone() {
		FoodItem item = new FoodItem();
		
		item.itemNumber = itemNumber;
		item.dailyNutrition = dailyNutrition.clone();
		item.cost = cost;
		
		return item;
	}			
}
