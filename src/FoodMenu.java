import java.util.ArrayList;

public class FoodMenu {
	ArrayList<FoodItem> menu = new ArrayList<FoodItem>();
	
	public FoodMenu() {
		menu.add(new FoodItem(1,1,3,4,5,6,12));		// F1
		menu.add(new FoodItem(2,2,4,3,2,5,12));		// F2
		menu.add(new FoodItem(3,2,1,5,2,4,9));		// F3
		menu.add(new FoodItem(4,2,1,5,2,4,9));		// F4
		menu.add(new FoodItem(5,6,1,2,2,3,5));		// F5
		menu.add(new FoodItem(6,1,6,1,4,7,8));		// F6
		menu.add(new FoodItem(7,4,2,2,6,1,9));		// F7
		menu.add(new FoodItem(8,1,5,3,2,5,12));		// F8
		menu.add(new FoodItem(9,3,2,3,6,2,9));		// F9
		menu.add(new FoodItem(10,7,1,3,2,3,7));		// F10
		menu.add(new FoodItem(11,2,6,1,4,1,8));		// F11
		menu.add(new FoodItem(12,2,1,6,2,3,11));	// F12
		menu.add(new FoodItem(13,2,5,1,2,7,10));	// F13
		menu.add(new FoodItem(14,6,2,2,1,2,8));		// F14
		menu.add(new FoodItem(15,3,1,1,3,6,9));		// F15
		menu.add(new FoodItem(16,5,5,4,2,1,15));	// F16
		menu.add(new FoodItem(17,2,4,3,1,2,14));	// F17
		menu.add(new FoodItem(18,5,6,1,4,0,8));		// F18
		menu.add(new FoodItem(19,3,2,3,6,2,9));		// F19
		menu.add(new FoodItem(20,2,6,1,4,1,8));		// F20
	}
	
	public FoodItem getRandomItem() {
		return menu.get((int)(Math.random() * menu.size()) );
	}	
}
