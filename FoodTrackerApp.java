package com.FridgeAlert;
import java.io.File;
import java.util.Scanner;

public class FoodTrackerApp {
	
	final static int millisecondsInDay = 86400000;
	static int warningTime = 3;
	static int grocGenerate = 7;
	static Mode m = Mode.FRIDGE;
	static long prevTime;
	
	public static void main(String[] args) {
		
		//TODO read up on file objects.
		File memory = null;
		//TODO build parser and parse file to build info
		//TODO change prevTime to parsed info
		prevTime = 0;
		GroceryList list = new GroceryList();
		//TODO rename class as needed
		Storage food = null;
		//TODO make command list
		String commandList = ""
				+ "Type any of these commands and press enter when ready.\n"
				+ "exit: saves data and exits the program.\n"
				+ "find: asks for a food to search for.\n"
				+ "h: displays a list of commands.\n"
				+ "help: displays a list of commands.\n"
				+ "look: asks for a food to search for.\n"
				+ "mode: returns to mode setup (fridge, freezer, pantry).\n"
				+ "quit: saves data and quits the program.\n"
				+ "search: asks for a food to search for.\n"
				+ "setup: returns to the setup.\n"
				+ "warnings: prints the information on the foods about to"
				+ "\nexpire, if any, as well as the grocery list, if ready.\n";
		
		boolean running = true;
		boolean setup = true;
		Scanner scone = new Scanner(System.in);
		
		System.out.println("Welcome to the food tracker app.");
		//Main loop that runs the program.
		while(running) {
			String input = "";
			
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
				//TODO add more setup as needed.
				System.out.println("Alright, that looks like everything for"
						+ " now. Remember to change these setting as needed.");
				System.out.println("If at any point you need information about"
						+ "what commands you can use,\ntype 'help' or 'h' and "
						+ "press enter.");
				setup = false;
			}
			
			//First interaction with the user after setup
			System.out.println("Where would you like to look?"
					+ "(fridge, freezer, pantry)");
			input = scone.nextLine();
			input = input.toUpperCase();
			if(input.equals("EXIT")) {
				FoodTrackerApp.exit(memory);
				running = false;
				break;
			}
			if(input.equals("HELP")) {
				System.out.println(commandList);
			}
			if(input.equals("SETUP")) {
				setup = true;
			}
			else if(!(input.equals("FREEZER")||input.equals("PANTRY")||
					input.equals("FRIDGE"))) {
				System.out.println("Invalid location. Please select either"
						+ "fridge, freezer, or pantry.");
				System.out.println("Default location selected: Fridge.");
				input = "FRIDGE";
			}
			else {
				//Secondary loop where input commands are given.
				/*
				 * Command list:
				 * help/h: prints command list
				 * quit: quits the program, you quitter...
				 * exit: saves data and exits program
				 * up: returns to storage selection
				 * mode: prints the current storage location
				 * find/look/search: searches for food in current location
				 * setup: runs the setup
				 * warnings: calls the functions that print food about to 
				 * 	expire and the grocery list
				 * 
				 */
				FoodTrackerApp.printUpdates(food, list, 
						System.currentTimeMillis());
				while(running) {
					//TODO put the switch case block here for commands given
				}
			}
		}
		
		scone.close();
		System.out.println("Goodbye.");

	}
	
	/**
	 * 
	 * @param f
	 * @param p
	 */
	public static void exit(File f/*, FTAParser p*/) {
		//TODO use parser to write to file
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
	private static void printGroceryList(Storage s, GroceryList l) {
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
	private static void printCloseToExpiring(Storage s) {
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
	 * Prints the current storage location being interacted with.
	 * @param m
	 */
	public static void printMode(Mode m) {
		System.out.print("Current mode is: ");
		switch (m) {
			case FREEZER:{
				System.out.println("freezer.");
				break;
			}
			case PANTRY:{
				System.out.println("pantry.");
				break;
			}
			default:{
				System.out.println("fridge.");
			}
		}
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

}