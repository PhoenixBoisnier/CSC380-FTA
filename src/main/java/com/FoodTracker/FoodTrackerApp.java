package main.java.com.FoodTracker;
import java.util.HashMap;
import java.util.Scanner;

public class FoodTrackerApp {
	
	public final static long millisecondsInDay = 
			Integer.toUnsignedLong(86400000);
	private static int warningTime = 3;
	private static int grocGenerate = 7;
	private static int freezerTime = 30;
	private static long prevTime;
	private int exists = 0;
	private Storage food;
	private GroceryList list;
	private HashMap<String, Food> favorites = new HashMap<>();
	private String commandList = ""
			+ "Type any of these commands and press enter when ready.\n"
			+ "add: Asks for info about food to be stored.\n"
			+ "display: asks for display type, then displays all food.\n"
			+ "empty: empties all storage locations. Cannot be undone.\n"
			+ "exit: Saves data and exits the program.\n"
			+ "expired: lists all expired foods then asks the user\n"
			+ "if they would like them added to the grocery list and \n"
			+ "removed from their locations, only removed, or left alone.\n"
			+ "favorites: opens favorites command.\n"
			+ "find: Asks for a food to search for.\n"
			+ "h: Displays a list of commands.\n"
			+ "help: Displays a list of commands.\n"
			+ "leftover: adds a leftover to the fridge or freezer or\n"
			+ "checks for leftovers."
			+ "list: Prints the grocery list, regardless of status.\n"
			+ "look: Asks for a food to search for.\n"
			+ "quit: Saves data and quits the program.\n"
			+ "remove: Removes a food from the inventory.\n"
			+ "search: Asks for a food to search for.\n"
			+ "setup: Returns to the setup.\n"
			+ "warnings: Prints the information on the foods about to\n"
			+ "expire, if any, as well as the grocery list, if ready.\n";
	
	/* Overall to-do list
	 */
	/*
	 *10: Stores food by category-----------------------------------------done
	 *10: Displays food available based on some order---------------------done
	 *10: Can add/remove food to the list---------------------------------done
	 *10: Persistent memory-----------------------------------------------done
	 *9: Command menu-----------------------------------------------------done
	 *7: Grocery list generation------------------------------------------done
	 *7: Search-able lists------------------------------------------------done
	 *5: Leftover storage/retrieval---------------------------------------done
	 *3: Favorite foods listing-------------------------------------------done
	 *
	 *10: User can use this program to store food in a database.----------done
	 *2: User can see stored food based on expiration date/other info.----done
	 *1: User's input is stored in categories.----------------------------done
	 *5: User's info is stored between sessions.--------------------------done
	 *3: User interacts with the program via command menu.----------------done
	 *3: User can search lists by food name/other.------------------------done
	 *2: Program will generate shopping lists for user.-------------------done
	 *2: User can store special food type, "leftovers."-------------------done
	 *2: User can store and retrieve favorite foods.----------------------done
	 *
	 */
	
	public FoodTrackerApp(FTAParser p) {
		
		this.food = p.getStorage();
		this.list = p.getList();
		exists = p.getExists();
		warningTime = p.getDaysWarning();
		grocGenerate = p.getListGenerate();
		freezerTime = p.getFreezerTime();
		
	}
	
	/**
	 * This method saves the current data into a text file using the parser.
	 * @param p
	 * @param l
	 * @param s
	 */
	public void exit() {
		new FTAParser().saveFile(list, food, favorites,
				warningTime, grocGenerate, freezerTime);
	}
	
	/**
	 * Finds and returns a food if it has been added to favorites.
	 * @param foodName
	 * @return
	 * @throws FavoriteFoodException
	 */
	public Food findFave(String foodName) throws FavoriteFoodException {
		if(favorites.containsKey(foodName)) {
			return favorites.get(foodName);
		}
		else throw new FavoriteFoodException();
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
		input = scone.nextLine();
		switch (input.toUpperCase()) {
			case "FREEZER" :{
				try {
					Food f = makeFood(scone);
					food.AddFood(f, Mode.FREEZER, list);
					favorites.put(f.getName(), f);
				} catch (AddFoodException e) {
					System.out.println("Food addition aborted.");
				}
				break;
			}
			case "PANTRY" :{
				try {
					Food f = makeFood(scone);
					food.AddFood(f, Mode.PANTRY, list);
					favorites.put(f.getName(), f);
				} catch (AddFoodException e) {
					System.out.println("Food addition aborted.");
				}
				break;
			}
			default :{
				try {
					Food f = makeFood(scone);
					food.AddFood(f, Mode.FRIDGE, list);
					favorites.put(f.getName(), f);
				} catch (AddFoodException e) {
					System.out.println("Food addition aborted.");
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
		String yn = "N";
		if(favorites.containsKey(foodName)) {
			System.out.println("This food was added before. Would you like to use\n"
					+ "the same information?");
			System.out.println(favorites.get(foodName));
			System.out.println("y/n");
			yn = scone.nextLine();
			if(yn.toUpperCase().equals("Y")) {
				return favorites.get(foodName);
			}
		}
		if(favorites.containsKey(foodName)) {
			favorites.remove(foodName);
		}
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
	 * if the location specified does not exist. If found, it will return
	 * the location it was found in if it matches the specified location, 
	 * otherwise will only specify that it was found. If it is not found,
	 * the user will be notified.
	 * @param scone
	 * @param input
	 * @return
	 */
	public String findIt(Scanner scone, String input) {
		String retVal = "";
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
					retVal += input+" was found in the "+modeInput+".";
				}
				else {
					retVal += input+" was not found.";
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
					retVal += input+" was found in the "+modeInput+".";
				}
				else {
					retVal += input+" was not found.";
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
					retVal += input+" was found in the "+modeInput+".";
				}
				else {
					retVal += input+" was not found.";
				}
				break;
			}
			default :{
				System.out.println("Location not spcified. "
						+ "Searching entire storage.");
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
					retVal += input+" was found.";
				}
				else {
					retVal += input+" was not found.";
				}
				break;
			}
		}
		return retVal;
	}
	
	/**
	 * This method prints out the foods about to expire as well as the 
	 * grocery list (as long as the list is due by time).
	 * @param s
	 * @param l
	 * @param t
	 * @return
	 */
	public String printUpdates(long t) {
		System.out.println();
		System.out.println("Caution; these foods are close to "
				+ "expiring: ");
		if(((System.currentTimeMillis()/1000)-(prevTime/1000))>=
				grocGenerate*millisecondsInDay/1000) {
			return printCloseToExpiring()+"\n"+printGroceryList();
		}
		else return printCloseToExpiring();
		
	}
	
	/**
	 * Will print the grocery list if the list is due to be generated.
	 * @param s
	 * @param l
	 * @return
	 */
	public String printGroceryList() {
		String printVal = "It's time to go shopping. Here is "
				+ "your grocery list:\n";
		System.out.println();
		list.checkInventory(food.getFreezer());
		list.checkInventory(food.getFridge());
		list.checkInventory(food.getPantry());
		return printVal+list.toString();
	}
	
	/**
	 * This checks to see what foods are close to expiring and prints those
	 * whose expiration date will show up in the next "t" days. The food 
	 * class takes care of the calculations and will return true or false
	 * if it is close to expiring. If it is, it is added to the list and
	 * printed for the user to see.
	 * @param s
	 * @param t
	 * @return
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
		return value; 
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
	public void removeFood(Scanner scone) {
		System.out.println("Where is the food being removed from?");
		String mode = scone.nextLine();
		System.out.println("What is the food being removed?");
		String foodName = scone.nextLine();
		Mode m;
		switch (mode.toUpperCase()) {
			case "FREEZER" :{
				m = Mode.FREEZER;
				break;
			}
			case "PANTRY" :{
				m = Mode.PANTRY;
				break;
			}
			default :{
				m = Mode.FRIDGE;
				break;
			}
		}
		Food f = new Food(foodName, 0.0, 0);
		food.remove(f,m,list);
	}
	
	/**
	 * A method to add all the expired foods from the storage and place them into
	 * the grocery list.
	 */
	public String addToListExpired(Scanner scone) {
		System.out.println("What would you like to do with the expired food?");
		System.out.println("1 - Remove expired foods and add to grocery list.");
		System.out.println("2 - Remove expired foods and do not add to list.");
		System.out.println("3 - Do nothing.");
		String input = scone.nextLine();
		String retVal = "No action performed.";
		switch (input) {
			case "1" :{
				retVal = "Added expired foods to grocery list.";
				retVal+= "\nExpired foods removed from storage.";
				for(int i = 0; i<food.getFreezer().size(); i++) {
					if(food.getFreezer().get(i).isExpired()) {
						food.remove(food.getFreezer().get(i),
								Mode.FREEZER, list);
						i--;
					}
				}
				for(int i = 0; i<food.getFridge().size(); i++) {
					if(food.getFridge().get(i).isExpired()) {
						food.remove(food.getFridge().get(i),
								Mode.FRIDGE, list);
						i--;
					}
				}
				for(int i = 0; i<food.getPantry().size(); i++) {
					if(food.getPantry().get(i).isExpired()) {
						food.remove(food.getPantry().get(i),
								Mode.PANTRY, list);
						i--;
					}
				}
				break;
			}
			case "2" :{
				retVal = "Expired foods only removed from storage.";
				for(int i = 0; i<food.getFreezer().size(); i++) {
					if(food.getFreezer().get(i).isExpired()) {
						food.getFreezer().remove(i);
						i--;
					}
				}
				for(int i = 0; i<food.getFridge().size(); i++) {
					if(food.getFridge().get(i).isExpired()) {
						food.getFridge().remove(i);
						i--;
					}
				}
				for(int i = 0; i<food.getPantry().size(); i++) {
					if(food.getPantry().get(i).isExpired()) {
						food.getPantry().remove(i);
						i--;
					}
				}
				break;
			}
			default :{
				//Because people are bad at things.
			}
		}
		return retVal;
	}
	
	/**
	 * Calls storage's addLeftover method.
	 * @param scone
	 */
	public void addLeftovers(Scanner scone) {
		food.addLeftover(scone);
	}
	
	/**
	 * Finds leftovers in the storage.
	 * @param scone
	 * @param food
	 * @return
	 */
	public String findLeftovers() {
		String retVal = "These are the leftovers present:\n";
		for(int i = 0; i<food.getFridge().size(); i++) {
			if(food.getFridge().get(i).isLeftover()) {
				retVal+="\t"+food.getFridge().get(i).getName()+" in fridge";
			}
		}
		for(int i = 0; i<food.getFreezer().size(); i++) {
			if(food.getFreezer().get(i).isLeftover()) {
				retVal+="\t"+food.getFreezer().get(i).getName()+" in freezer";
			}
		}
		if(retVal.equals("These are the leftovers present:\n")) {
			retVal+="\tNo leftovers are present";
		}
		return retVal;
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
	
	/**
	 * Returns the time leftover food stays good in the freezer.
	 * @return
	 */
	public int getFreezerTime() {
		return freezerTime;
	}
	
	/**
	 * Sets the time leftover food stays good in the freezer for.
	 * @param t
	 */
	public void setFreezerTime(int t) {
		freezerTime = t;
	}
	
	/**
	 * Gets the storage class from the app.
	 * @return
	 */
	public Storage getFoods() {
		return food;
	}
	
	/**
	 * Method used by test class.
	 * @param s
	 * @param l
	 */
	public void testMethod1(Storage s, GroceryList l) {
		food = s;
		list = l;
	}
	
	/**
	 * Method used by test class.
	 * @param f
	 */
	public void testMethod2(HashMap<String,Food> f) {
		favorites = f;
	}
	
	/**
	 * Gets the grocery list.
	 * @return
	 */
	public GroceryList getList() {
		return list;
	}
	
	/**
	 * Resets the storage in the app if the input it "Y"
	 * @param y
	 */
	public void resetAllFoodStorage(String y) {
		if(y.equals("Y")) {
			this.food = new Storage();
		}
	}
}