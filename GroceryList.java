package com.FridgeAlert;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class keeps track of what foods will need to be purchased to maintain
 * the current store of foods in the storage locations. When a food is removed
 * from the storage location, it should be added to this class in the main class.
 * @author phoenix
 *
 */
public class GroceryList {
	
	ArrayList<Food> list = new ArrayList<>();
	HashMap<String, Food> compareList = new HashMap<>();
	
	public GroceryList() {
		
	}
	
	/**
	 * Method used to manually add food to the grocery list.
	 * @param f
	 */
	public void manualAdd(Food f) {
		list.add(f);
		compareList.put(f.getName(), f);
	}
	
	/**
	 * Returns the size of the grocery list.
	 * @return
	 */
	public int getSize() {
		return list.size();
	}
	
	/**
	 * Used to retrieve a specific food item from the list
	 * @param i
	 * @return
	 */
	public Food getFood(int i) {
		return list.get(i);
	}
	
	/**
	 * Takes a list of food and adds it to the grocery list.
	 * @param foods
	 */
	public void autoAdd(ArrayList<Food> foods) {
		for(Food f : foods) {
			list.add(f);
			compareList.put(f.getName(), f);
		}
	}
	
	/**
	 * Be sure to call checkInventory() for each section of inventory before
	 * calling toString().
	 * 
	 * Returns the list of foods in the order in which food was 
	 * added to the grocery list.
	 */
	public String toString() {
		String s = "";
		for(Food f : list) {
			s+=f.getName();
			if(f.getCost()!=0) {
				s+=", cost: "+f.getCost();
			}
			s+="\n";
		}
		if(s.equals("")) {
			s = "You don't have anything in your list.";
		}
		return s;
	}
	
	/**
	 * A method that takes an array list of food as the parameter and removes
	 * any food found in the parameter from this grocery list's lists. This 
	 * prevents the grocery list from generating duplicate foods that already
	 * exist in the storage location. This method should be called before
	 * any printed instance of toString(). This method can also be used to 
	 * remove items from the list in general.
	 * @param storageLoc
	 */
	public void checkInventory(ArrayList<Food> storageLoc) {
		for(Food f : storageLoc) {
			if(compareList.containsKey(f.getName())) {
				this.removeItem(f.getName());
			}
		}
	}
	
	/**
	 * A method used to traverse the list of food and remove the item
	 * from both the compareList and actual list.
	 * @param s
	 */
	public void removeItem(String s) {
		if(compareList.containsKey(s)) {
			compareList.remove(s);
			for(int i = 0; i<list.size(); i++) {
				if(list.get(i).getName().equals(s)) {
					list.remove(i);
					break;
				}
			}
		}
	}

}
