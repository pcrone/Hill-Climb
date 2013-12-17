import java.util.ArrayList;

public class FoodMenu {
	ArrayList<FoodItem> menu = new ArrayList<FoodItem>();
	
	public FoodMenu() {
		menu.add(new FoodItem("F1",1,3,4,5,6,12));	// F1
		menu.add(new FoodItem("F2",2,4,3,2,5,12));	// F2
		menu.add(new FoodItem("F3",2,1,5,2,4,9));	// F3
		menu.add(new FoodItem("F4",2,1,5,2,4,9));	// F4
		menu.add(new FoodItem("F5",6,1,2,2,3,5));	// F5
		menu.add(new FoodItem("F6",1,6,1,4,7,8));	// F6
		menu.add(new FoodItem("F7",4,2,2,6,1,9));	// F7
		menu.add(new FoodItem("F8",1,5,3,2,5,12));	// F8
		menu.add(new FoodItem("F9",3,2,3,6,2,9));	// F9
		menu.add(new FoodItem("F10",7,1,3,2,3,7));	// F10
		menu.add(new FoodItem("F11",2,6,1,4,1,8));	// F11
		menu.add(new FoodItem("F12",2,1,6,2,3,11));	// F12
		menu.add(new FoodItem("F13",2,5,1,2,7,10));	// F13
		menu.add(new FoodItem("F14",6,2,2,1,2,8));	// F14
		menu.add(new FoodItem("F15",3,1,1,3,6,9));	// F15
		menu.add(new FoodItem("F16",5,5,4,2,1,15));	// F16
		menu.add(new FoodItem("F17",2,4,3,1,2,14));	// F17
		menu.add(new FoodItem("F18",5,6,1,4,0,8));	// F18
		menu.add(new FoodItem("F19",3,2,3,6,2,9));	// F19
		menu.add(new FoodItem("F20",2,6,1,4,1,8));	// F20
	}
	
	public FoodItem getRandomItem() {
		return menu.get((int)(Math.random() * (menu.size()-1)) );
	}	
}
