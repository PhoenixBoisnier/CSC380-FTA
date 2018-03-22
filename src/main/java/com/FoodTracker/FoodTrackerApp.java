package main.java.com.FoodTracker;
import java.util.Scanner;

public class FoodTrackerApp {
	
	public final static int millisecondsInDay = 86400000;
	private static int warningTime = 3;
	private static int grocGenerate = 7;
	private static long prevTime;
	private int exists = 0;
	private Storage food;
	private GroceryList list;
	private FTAParser p;
	private Scanner scone;
	//TODO make command list
	private String commandList = ""
			+ "Type any of these commands and press enter when ready.\n"
			+ "add: Asks for info about food to be stored."
			+ "exit: Saves data and exits the program.\n"
			//TODO expired: lists all expired foods then asks the user\n
			//if they would like them added to the grocery list and \n
			//removed from their locations, only removed, or left alone.
			+ "find: Asks for a food to search for.\n"
			+ "h: Displays a list of commands.\n"
			+ "help: Displays a list of commands.\n"
			//TODO + "leftover: adds a leftover to the fridge or freezer.\n"
			+ "list: Prints the grocery list, regardless of status.\n"
			+ "look: Asks for a food to search for.\n"
			+ "quit: Saves data and quits the program.\n"
			+ "remove: Removes a food from the invetory.\n"
			+ "search: Asks for a food to search for.\n"
			+ "setup: Returns to the setup.\n"
			+ "warnings: Prints the information on the foods about to"
			+ "\nexpire, if any, as well as the grocery list, if ready.\n";
	
	/* Overall to-do list
	 * TODO:	rework foodTrackerApp to return strings for all methods that print
	 * 			finish test class for FoodTrackerApp
	 * 			add tests for leftovers
	 * 			finish unimplemented commands
	 * 			fix parser
	 */
	
	/*TODO check this throughout 
	 *10: Stores food by category as well as other info, such as expiration date
	 *10: Displays food available based on some order
	 *10: Can add/remove food to the list---------------------------------done
	 *10: Persistent memory
	 *9: Command menu
	 *7: Grocery list generation------------------------------------------done
	 *7: Search-able lists------------------------------------------------done
	 *5: Leftover storage/retrieval
	 *3: Favorite foods listing
	 *
	 *10: User can use this program to store food in a database.----------done
	 *2: User can see stored food based on expiration date/other info.
	 *1: User's input is stored in categories.----------------------------done
	 *5: User's info is stored between sessions.
	 *3: User interacts with the program via command menu.
	 *3: User can search lists by food name/other.------------------------done
	 *2: Program will generate shopping lists for user.-------------------done
	 *2: User can store special food type, "leftovers."
	 *2: User can store and retrieve favorite foods.
	 *
	 */
	
	public FoodTrackerApp(FTAParser p, GroceryList list, Storage food, 
			Scanner scone) {
		
		this.food = food;
		this.p = p;
		this.list = list;
		exists = p.getExists();
		warningTime = p.getDaysWarning();
		grocGenerate = p.getListGenerate();
		this.scone = scone;
		
	}
	
	/**
	 * This method saves the current data into a text file using the parser.
	 * @param p
	 * @param l
	 * @param s
	 */
	public void exit() {
		//TODO fix the parser
		p.saveFile(list, food, warningTime, grocGenerate);
	}
	
	/**
	 * This method is used to put foods into their storage locations. If no 
	 * valid location is specified, the new food will be put into the fridge.
	 * @param scone
	 * @param input
	 * @param food
	 * @param list
	 */
	public void addIt(Scanner scone, String input) {
		System.out.println("Where will you be storing this food?");
		System.out.println("Default location is the fridge.");
		input = scone.next();
		switch (input.toUpperCase()) {
			case "FREEZER" :{
				try {
					food.AddFood(makeFood(scone), Mode.FREEZER, list);
				} catch (AddFoodException e) {
					e.printStackTrace();
				}
				break;
			}
			case "PANTRY" :{
				try {
					food.AddFood(makeFood(scone), Mode.PANTRY, list);
				} catch (AddFoodException e) {
					e.printStackTrace();
				}
				break;
			}
			default :{
				try {
					food.AddFood(makeFood(scone), Mode.FRIDGE, list);
				} catch (AddFoodException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * This method is used by the addIt method to take user input and generate
	 * a new food object.
	 * @param scone
	 * @param input
	 * @throws AddFoodException 
	 */
	public Food makeFood(Scanner scone) throws AddFoodException {
		System.out.println("What is this food called?");
		String foodName = scone.nextLine();
		System.out.println("How much did this food cost?");
		double foodCost = 0.0;
		try {
			foodCost = Double.parseDouble(scone.nextLine());
		}
		catch (NumberFormatException e) {
			System.out.println("Invalid input, food's cost is set to 0.");
		}
		System.out.println("How many days until it expires?");
		int foodExpr = -1;
		while (foodExpr < 0) {
			try {
				foodExpr = Integer.parseInt(scone.nextLine());
			}
			catch (NumberFormatException e) {
				System.out.println("Not a valid input. Expiration is a number"
						+ " of days.");
			}
		}
		System.out.println("Adding food "+foodName+" costing "+foodCost+
				" expriring in "+foodExpr+" days. \nDoes this look correct? y/n");
		String correct = scone.nextLine();
		if(correct.toUpperCase().equals("Y")) {
			return new Food(foodName, foodCost, foodExpr);
		}
		else {
			throw new AddFoodException();
		}
	}
	
	/**
	 * This method searches through the storage location specified, or all
	 * if the location specified does not exist. If found, it will output
	 * the location it was found in if it matches the specified location, 
	 * otherwise will only specify that it was found. If it is not found,
	 * the user will be notified.
	 * @param scone
	 * @param input
	 * @param food
	 */
	public String findIt(Scanner scone, String input) {
		System.out.println("Where would you like to search?");
		String modeInput = scone.nextLine();
		System.out.println("What would you like to search for?");
		input = scone.nextLine();
		boolean present = false;
		switch (modeInput.toUpperCase()) {
			case "FRIDGE" :{
				for(int i = 0; i<food.getFridge().size(); i++) {
					if(food.getFridge().get(i).getName().toUpperCase().
							equals(input.toUpperCase())) {
						present = true;
						break;
					}
				}
				if(present) {
					System.out.println(input+" was found in the "+
							modeInput+".");
				}
				else {
					System.out.println(input+" was not found.");
				}
				break;
			}
			case "FREEZER" :{
				for(int i = 0; i<food.getFreezer().size(); i++) {
					if(food.getFreezer().get(i).getName().toUpperCase().
							equals(input.toUpperCase())) {
						present = true;
						break;
					}
				}
				if(present) {
					System.out.println(input+" was found in the "+
							modeInput+".");
				}
				else {
					System.out.println(input+" was not found.");
				}
				break;
			}
			case "PANTRY" :{
				for(int i = 0; i<food.getPantry().size(); i++) {
					if(food.getPantry().get(i).getName().toUpperCase().
							equals(input.toUpperCase())) {
						present = true;
						break;
					}
				}
				if(present) {
					System.out.println(input+" was found in the "+
							modeInput+".");
				}
				else {
					System.out.println(input+" was not found.");
				}
				break;
			}
			default :{
				System.out.println("Location not found. Searching entire storage.");
				for(int i = 0; i<food.getPantry().size(); i++) {
					if(food.getPantry().get(i).getName().toUpperCase().
							equals(input.toUpperCase())) {
						present = true;
						break;
					}
				}
				for(int i = 0; i<food.getFreezer().size(); i++) {
					if(food.getFreezer().get(i).getName().toUpperCase().
							equals(input.toUpperCase())) {
						present = true;
						break;
					}
				}
				for(int i = 0; i<food.getFridge().size(); i++) {
					if(food.getFridge().get(i).getName().toUpperCase().
							equals(input.toUpperCase())) {
						present = true;
						break;
					}
				}
				if(present) {
					System.out.println(input+" was found.");
				}
				else {
					System.out.println(input+" was not found.");
				}
				break;
			}
		}
	}
	
	/**
	 * This method prints out the foods about to expire as well as the 
	 * grocery list (as long as the list is due by time).
	 * @param s
	 * @param l
	 * @param t
	 */
	public String printUpdates(long t) {
		System.out.println();
		System.out.println("Caution; these foods are close to "
				+ "expiring: ");
		return printCloseToExpiring()+"\n"+printGroceryList();
	}
	
	/**
	 * Will print the grocery list if the list is due to be generated.
	 * @param s
	 * @param l
	 */
	public String printGroceryList() {
		if(((System.currentTimeMillis()/1000)-(prevTime/1000))>=
				grocGenerate*millisecondsInDay/1000) {
			String printVal = "It's time to go shopping. Here is "
					+ "your grocery list:\n";
			System.out.println();
			list.checkInventory(food.getFreezer());
			list.checkInventory(food.getFridge());
			list.checkInventory(food.getPantry());
			if(list.toString().equals("")) {
				System.out.println("There's nothing on your shopping list.\n");
			}
			else System.out.println(printVal+list);
			System.out.println();
		}
	}
	
	/**
	 * This checks to see what foods are close to expiring and prints those
	 * whose expiration date will show up in the next "t" days. The food 
	 * class takes care of the calculations and will return true or false
	 * if it is close to expiring. If it is, it is added to the list and
	 * printed for the user to see.
	 * @param s
	 * @param t
	 */
	public String printCloseToExpiring() {
		String value = "Foods about to expire:\n";
		for(Food foods : food.getFridge()) {
			if(foods.aboutToExpire(warningTime)){
				value+="\t"+foods.getName()+"\n";
			}
		}
		for(Food foods : food.getFreezer()) {
			if(foods.aboutToExpire(warningTime)){
				value+="\t"+foods.getName()+"\n";
			}
		}
		for(Food foods : food.getPantry()) {
			if(foods.aboutToExpire(warningTime)){
				value+="\t"+foods.getName()+"\n";
			}
		}
		if(value.equals("Foods about to expire:\n")) {
			value = "No foods are close to expiring.";
		}
		System.out.println(value); 
	}
	
	/**
	 * Prints out which foods are expired, if any.
	 * @param s
	 */
	public String expiredFoods() {
		String value = "Expired foods:\n";
		for(Food foods : food.getFridge()) {
			if(foods.isExpired()){
				value+="\t"+foods.getName()+"\n";
			}
		}
		for(Food foods : food.getFreezer()) {
			if(foods.isExpired()){
				value+="\t"+foods.getName()+"\n";
			}
		}
		for(Food foods : food.getPantry()) {
			if(foods.isExpired()){
				value+="\t"+foods.getName()+"\n";
			}
		}
		if(value.equals("Expired foods:\n")) {
			value = "No foods are expired.";
		}
		return value; 
	}
	
	/**
	 * This method will remove food from the storage location and add it to the
	 * grocery list.
	 * @param food
	 * @param f
	 * @param l
	 * @param m
	 */
	public void removeFood(Storage food, Food f, GroceryList l, Mode m) {
		food.remove(f,m,l);
		l.manualAdd(f);
	}
	
	/**
	 * A method to add all the expired foods from the storage and place them into
	 * the grocery list.\
	 */
	public void addToListExpired() {
		//TODO
	}
	
	/**
	 * Calls storage's addLeftover method.
	 * @param scone
	 */
	public void addLeftovers(Scanner scone) {
		//TODO add this command to switch case
		food.addLeftover(scone);
	}
	
	/**
	 * Finds leftovers in the storage location.
	 * @param scone
	 * @param food
	 */
	public String findLeftovers(Scanner scone) {
		//TODO
	}
	
	/**
	 * Sets warning time.
	 * @param time
	 */
	public void setWarningTime(int time) {
		warningTime = time;
	}
	
	/**
	 * Gets warning time.
	 * @return
	 */
	public int getWarningTime() {
		return warningTime;
	}
	
	/**
	 * Sets grocery list generation time.
	 */
	public void setGrocGenerate(int freq) {
		grocGenerate = freq;
	}
	
	/**
	 * Gets grocery list generation time.
	 * @return
	 */
	public int getGrocGenerate() {
		return grocGenerate;
	}
	
	/**
	 * Returns 1 if load file existed.
	 */
	public int getExists() {
		return exists;
	}
	
	/**
	 * Returns the command list.
	 * @return
	 */
	public String getCommandList() {
		return commandList;
	}

}