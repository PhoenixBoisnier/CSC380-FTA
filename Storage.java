package com.FridgeAlert;
import java.util.ArrayList;

/**
 * This class is the main storage location for the bulk of the app.
 * All food is stored here and any modifications of the grocery list
 * are tracked and managed by this class. Added methods to get individual
 * storage locations.
 * @author phoenix
 *
 */
public class Storage {
	
	private ArrayList<Food> fridge = new ArrayList<>();
	private ArrayList<Food> freezer = new ArrayList<>();
	private ArrayList<Food> pantry = new ArrayList<>();
	
	/**
	 * This method adds food to the inventory and removes it from the 
	 * grocery list.
	 * @param f
	 * @param m
	 * @param l
	 */
	public void AddFood(Food f, Mode m, GroceryList l) {
		switch(m) {
			case FREEZER:{
				freezer.add(f);
				l.removeItem(f.getName());
				break;
			}
			case PANTRY:{
				pantry.add(f);
				l.removeItem(f.getName());
				break;
			}
			default :{
				fridge.add(f);
				l.removeItem(f.getName());
				break;
			}
		}
	}

	/**
	 * This method removes items from the storage locations and adds
	 * them into the grocery list.
	 * @param f
	 * @param m
	 * @param l
	 */
	public void remove(Food f, Mode m, GroceryList l) {
		//TODO
	}
	
	public ArrayList<Food> getFreezer(){
		return freezer;
	}
	
	public ArrayList<Food> getFridge(){
		return fridge;
	}
	
	public ArrayList<Food> getPantry(){
		return pantry;
	}

}
