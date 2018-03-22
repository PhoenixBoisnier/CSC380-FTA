package main.java.com.FoodTracker;
import java.util.ArrayList;
import java.util.Scanner;

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
	@SuppressWarnings("static-access")
	public void remove(Food f, Mode m, GroceryList l) {
		//If the food is a leftover, it will not be added to the grocery list.
		if(f.isLeftover) {
			if(m==Mode.FREEZER) {
				for(int i = 0; i<freezer.size(); i++) {
					if(freezer.get(i).getName().equals(f.getName())) {
						freezer.remove(i);
						System.out.println(f.getName()+" was removed.");
						break;
					}
				}
			}
			else {
				for(int i = 0; i<fridge.size(); i++) {
					if(fridge.get(i).getName().equals(f.getName())) {
						fridge.remove(i);
						System.out.println(f.getName()+" was removed.");
						break;
					}
				}
			}
		}
		//The food is not a leftover, so upon removal, it will be added to 
		//the grocery list.
		else {
			switch(m) {
				case FREEZER:{
					for(int i = 0; i<freezer.size(); i++) {
						if(freezer.get(i).getName().equals(f.getName())) {
							l.manualAdd(freezer.remove(i));
							System.out.println(f.getName()+" was removed.");
							break;
						}
					}
					break;
				}
				case PANTRY:{
					for(int i = 0; i<pantry.size(); i++) {
						if(pantry.get(i).getName().equals(f.getName())) {
							l.manualAdd(pantry.remove(i));
							System.out.println(f.getName()+" was removed.");
							break;
						}
					}
					break;
				}
				default :{
					for(int i = 0; i<fridge.size(); i++) {
						if(fridge.get(i).getName().equals(f.getName())) {
							l.manualAdd(fridge.remove(i));
							System.out.println(f.getName()+" was removed.");
							break;
						}
					}
					break;
				}
			}
		}
	}
	
	/**
	 * Method used to add special leftover type of food to the storage.
	 * @param scone]
	 */
	public void addLeftover(Scanner scone) {
		System.out.println("What is the leftover?");
		String name = scone.nextLine();
		boolean frozen = false;
		System.out.println("Will you be freezing this? y/n");
		String freeze = scone.nextLine();
		if(freeze.toUpperCase().equals("Y")) {
			frozen = true;
		}
		if(frozen) {
			freezer.add(new Leftover(name, frozen));
			System.out.println(name+" was added to the freezer.");
		}
		else {
			fridge.add(new Leftover(name, frozen));
			System.out.println(name+" was added to the fridge.");
		}
	}
	
	/**
	 * This method returns the freezer as an array list of food.
	 * @return
	 */
	public ArrayList<Food> getFreezer(){
		return freezer;
	}
	
	/**
	 * This method returns the fridge as an array list of food.
	 * @return
	 */
	public ArrayList<Food> getFridge(){
		return fridge;
	}
	
	/**
	 * This method returns the pantry as an array list of food.
	 * @return
	 */
	public ArrayList<Food> getPantry(){
		return pantry;
	}
	
	/**
	 * Returns the contents of each location as a single string list.
	 */
	public String toString() {
		String retVal = "This is the food currently present:\n";
		retVal+="\tFridge:\n";
		for(int i = 0; i<this.fridge.size(); i++) {
			retVal+="\t\t"+this.fridge.get(i).getName()+"\n";
		}
		if(this.fridge.size()==0) {
			retVal+="\t\tEmpty.\n";
		}
		retVal+="\tFreezer:\n";
		for(int i = 0; i<this.freezer.size(); i++) {
			retVal+="\t\t"+this.freezer.get(i).getName()+"\n";
		}
		if(this.freezer.size()==0) {
			retVal+="\t\tEmpty.\n";
		}
		retVal+="\tPantry:\n";
		for(int i = 0; i<this.pantry.size(); i++) {
			retVal+="\t\t"+this.pantry.get(i).getName()+"\n";
		}
		if(this.pantry.size()==0) {
			retVal+="\t\tEmpty.\n";
		}
		return retVal;
	}

}
