package main.java.com.FoodTracker;
import java.util.Scanner;

public class FoodTrackerApp {
	
	public final static int millisecondsInDay = 86400000;
	static int warningTime = 3;
	static int grocGenerate = 7;
	static Mode m = Mode.FRIDGE;
	static long prevTime;
	static int exists = 0;
	
	/* Overall to-do list
	 * TODO:	finish test class for FoodTrackerApp
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
	
	public static void main(String[] args) {
		
		GroceryList list = new GroceryList();
		Storage food = new Storage();
		FTAParser p = new FTAParser(list, food);
		list = p.getList();
		food = p.getStorage();
		exists = p.getExists();
		warningTime = p.getDaysWarning();
		grocGenerate = p.getListGenerate();
		//TODO make command list
		String commandList = ""
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
		
		boolean running = true;
		boolean setup = true;
		if(exists==1) {
			setup = false;
		}
		Scanner scone = new Scanner(System.in);
		
		System.out.println("Welcome to the food tracker app.");
		String input = "";
		//Main loop that runs the program.
		while(running) {
			
			
			//prints out foods about to expire as long as there are foods
			//prints out the grocery list as long as there are foods
			if(!setup) {
				FoodTrackerApp.printUpdates(food, list, 
						System.currentTimeMillis());
			}
			//otherwise perform first time setup.
			else {
				System.out.println("Welcome to your food tracker app setup.");
				System.out.println("We're going to get some setting "
						+ "squared away for you.");
				System.out.println("First, how soon before expiring would you"
						+ "like to recieve warnings? Default is 3 days.");
				System.out.println("This number is a number of days.");
				int num = -1;
				while(num<0) {
					try {
						num = Integer.parseInt(scone.nextLine());
					}
					catch(NumberFormatException e) {
						System.out.println("That was not a valid entry.");
						System.out.println("For now, we'll set it to 3.");
						System.out.println("This can be changed at a later"
								+ " time.");
						num = 3;
					}
					warningTime = num;
				}
				System.out.print("\nGreat! Your wanring timer is set to ");
				System.out.println(warningTime+" days before expiration.");
				System.out.println("Now, how often would you like your"
						+ " grocery list to be generated?");
				System.out.println("This is a number of days.");
				num = -1;
				while(num<0) {
					try {
						num = Integer.parseInt(scone.nextLine());
					}
					catch(NumberFormatException e) {
						System.out.println("That was not a valid entry.");
						System.out.println("For now, we'll set it to 7.");
						System.out.println("This can be changed at a later"
								+ " time.");
						num = 7;
					}
					grocGenerate = num;
				}
				System.out.println("\nAwesome! Your grocery list will be "
						+ "provided every "+grocGenerate+" days.");
				//TODO add leftover freezer duration to settings
				//TODO add more setup as needed.
				System.out.println("Alright, that looks like everything for"
						+ " now. Remember to change these setting as needed.");
				System.out.println("If at any point you need information about"
						+ "what commands you can use,\ntype 'help' or 'h' and "
						+ "press enter.");
				setup = false;
			}
			
			//First interaction with the user after setup
			while(!input.equals("EXIT")&&setup==false) {
				//TODO list each command to implement, then implement it
				//Secondary loop where input commands are given.
				/*
				done - "add: asks for info about food to be stored."
				done - "exit: saves data and exits the program.\n"
				+ "expired: lists all expired foods then asks the user\n"
				+ "if they would like them added to the grocery list and \n"
				+ "removed from their locations, only removed, or left alone."
				done - "find: asks for a food to search for.\n"
				done - "h: displays a list of commands.\n"
				done - "help: displays a list of commands.\n"
				+ "leftover: adds a leftover to the fridge or freezer.\n"
				+ "list: Prints the grocery list, regardless of status.\n"
				done - "look: asks for a food to search for.\n"
				done - "quit: saves data and quits the program.\n"
				+ "remove: removes a food from the invetory.\n"
				done - "search: asks for a food to search for.\n"
				done - "setup: returns to the setup.\n"
				+ "warnings: prints the information on the foods about to"
				+ "\nexpire, if any, as well as the grocery list, if ready.\n";
				 */
				System.out.println("What would you like to do?");
				input = scone.nextLine();
				input = input.toUpperCase();
				switch (input) {
					case "EXIT" :{
						FoodTrackerApp.exit(p, list, food);
						running = false;
						break;
					}
					case "QUIT" :{
						FoodTrackerApp.exit(p, list, food);
						running = false;
						break;
					}
					case "HELP" :{
						System.out.println(commandList);
						break;
					}
					case "H" :{
						System.out.println(commandList);
						break;
					}
					case "SETUP" :{
						setup = true;
						break;
					}
					case "SEARCH" :{
						FoodTrackerApp.findIt(scone, input, food);
						break;
					}
					case "FIND" :{
						FoodTrackerApp.findIt(scone, input, food);
						break;
					}
					case "LOOK" :{
						FoodTrackerApp.findIt(scone, input, food);
						break;
					}
					case "ADD" :{
						FoodTrackerApp.addIt(scone, input, food, list);
						break;
					}
					default :{
						System.out.println("Not a valid command.");
						System.out.println("Type 'help' or 'h' for a list of commands.");
					}
				}
			}
		}
		
		scone.close();
		System.out.println("Goodbye.");

	}
	
	/**
	 * This method saves the current data into a text file using the parser.
	 * @param p
	 * @param l
	 * @param s
	 */
	public static void exit(FTAParser p, GroceryList l, Storage s) {
		//TODO fix the parser
		p.saveFile(l, s, warningTime, grocGenerate);
	}
	
	/**
	 * This method is used to put foods into their storage locations. If no 
	 * valid location is specified, the new food will be put into the fridge.
	 * @param scone
	 * @param input
	 * @param food
	 * @param list
	 */
	public static void addIt(Scanner scone, String input, Storage food,
			GroceryList list) {
		System.out.println("Where will you be storing this food?");
		System.out.println("Default location is the fridge.");
		input = scone.next();
		switch (input.toUpperCase()) {
			case "FREEZER" :{
				try {
					food.AddFood(FoodTrackerApp.makeFood(scone), 
							Mode.FREEZER, list);
				} catch (AddFoodException e) {
					e.printStackTrace();
				}
				break;
			}
			case "PANTRY" :{
				try {
					food.AddFood(FoodTrackerApp.makeFood(scone), 
							Mode.PANTRY, list);
				} catch (AddFoodException e) {
					e.printStackTrace();
				}
				break;
			}
			default :{
				try {
					food.AddFood(FoodTrackerApp.makeFood(scone), 
							Mode.FRIDGE, list);
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
	public static Food makeFood(Scanner scone) throws AddFoodException {
		System.out.println("What is this food called?");
		String foodName = scone.next();
		System.out.println("How much did this food cost?");
		double foodCost = 0.0;
		try {
			foodCost = Double.parseDouble(scone.next());
		}
		catch (NumberFormatException e) {
			System.out.println("Invalid input, food's cost is set to 0.");
		}
		System.out.println("How many days until it expires?");
		int foodExpr = -1;
		while (foodExpr < 0) {
			try {
				foodExpr = Integer.parseInt(scone.next());
			}
			catch (NumberFormatException e) {
				System.out.println("Not a valid input. Expiration is a number"
						+ " of days.");
			}
		}
		System.out.println("Adding food "+foodName+" costing "+foodCost+
				" expriring in "+foodExpr+" days. \nDoes this look correct? y/n");
		String correct = scone.next();
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
	public static void findIt(Scanner scone, String input, Storage food) {
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
	public static void printUpdates(Storage s, GroceryList l, long t) {
		System.out.println();
		System.out.println("Caution; these foods are close to "
				+ "expiring: ");
		FoodTrackerApp.printCloseToExpiring(s);
		FoodTrackerApp.printGroceryList(s, l);
	}
	
	/**
	 * Will print the grocery list if the list is due to be generated.
	 * @param s
	 * @param l
	 */
	public static void printGroceryList(Storage s, GroceryList l) {
		if(((System.currentTimeMillis()/1000)-(prevTime/1000))>=
				grocGenerate*millisecondsInDay/1000) {
			String printVal = "It's time to go shopping. Here is "
					+ "your grocery list:\n";
			System.out.println();
			l.checkInventory(s.getFreezer());
			l.checkInventory(s.getFridge());
			l.checkInventory(s.getPantry());
			if(l.toString().equals("")) {
				System.out.println("There's nothing on your shopping list.\n");
			}
			else System.out.println(printVal+l);
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
	public static void printCloseToExpiring(Storage s) {
		String value = "Foods about to expire:\n";
		for(Food foods : s.getFridge()) {
			if(foods.aboutToExpire(warningTime)){
				value+="\t"+foods.getName()+"\n";
			}
		}
		for(Food foods : s.getFreezer()) {
			if(foods.aboutToExpire(warningTime)){
				value+="\t"+foods.getName()+"\n";
			}
		}
		for(Food foods : s.getPantry()) {
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
	public static void expiredFoods(Storage s) {
		String value = "Expired foods:\n";
		for(Food foods : s.getFridge()) {
			if(foods.isExpired()){
				value+="\t"+foods.getName()+"\n";
			}
		}
		for(Food foods : s.getFreezer()) {
			if(foods.isExpired()){
				value+="\t"+foods.getName()+"\n";
			}
		}
		for(Food foods : s.getPantry()) {
			if(foods.isExpired()){
				value+="\t"+foods.getName()+"\n";
			}
		}
		if(value.equals("Expired foods:\n")) {
			value = "No foods are expired.";
		}
		System.out.println(value); 
	}
	
	/**
	 * This method will remove food from the storage location and add it to the
	 * grocery list.
	 * @param food
	 * @param f
	 * @param l
	 * @param m
	 */
	public static void removeFood(Storage food, Food f, GroceryList l, Mode m) {
		food.remove(f,m,l);
		l.manualAdd(f);
	}
	
	/**
	 * A method to add all the expired foods from the storage and place them into
	 * the grocery list.
	 * @param food
	 * @param l
	 */
	public static void addToListExpired(Storage food, GroceryList l) {
		//TODO
	}
	
	/**
	 * Calls storage's addLeftover method.
	 * @param scone
	 * @param food
	 */
	public static void addLeftovers(Scanner scone, Storage food) {
		//TODO add this command to switch case
		food.addLeftover(scone);
	}

}