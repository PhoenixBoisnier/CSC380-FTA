package main.java.com.FoodTracker;

import java.util.Scanner;

public class FTA_CmdRunner {

	public static void main(String[] args) {
		
		GroceryList list = new GroceryList();
		Storage food = new Storage();
		FTAParser p = new FTAParser(list, food);
		list = p.getList();
		food = p.getStorage();
		Scanner scone = new Scanner(System.in);
		FoodTrackerApp app = new FoodTrackerApp(list, food);
		
		
		boolean running = true;
		boolean setup = true;
		if(app.getExists()==1) {
			setup = false;
		}
		
		System.out.println("Welcome to the food tracker app.");
		String input = "";
		//Main loop that runs the program.
		while(running) {
			
			
			//prints out foods about to expire as long as there are foods
			//prints out the grocery list as long as there are foods
			if(!setup) {
				app.printUpdates(System.currentTimeMillis());
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
					app.setWarningTime(num);
				}
				System.out.print("\nGreat! Your wanring timer is set to ");
				System.out.println(app.getWarningTime()+" days before expiration.");
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
					app.setGrocGenerate(num);
				}
				System.out.println("\nAwesome! Your grocery list will be "
						+ "provided every "+app.getGrocGenerate()+" days.");
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
				done - "expired: lists all expired foods then asks the user\n"
				done - "if they would like them added to the grocery list and \n"
				done - "removed from their locations, only removed, or left alone."
				done - "find: asks for a food to search for.\n"
				done - "h: displays a list of commands.\n"
				done - "help: displays a list of commands.\n"
				done - "leftover: adds a leftover to the fridge or freezer or\n"
				done - "checks for leftovers."
				done - "list: Prints the grocery list, regardless of status.\n"
				done - "look: asks for a food to search for.\n"
				done - "quit: saves data and quits the program.\n"
				done - "remove: removes a food from the inventory.\n"
				done - "search: asks for a food to search for.\n"
				done - "setup: returns to the setup.\n"
				done - "warnings: prints the information on the foods about to\n"
				done - "expire, if any, as well as the grocery list, if ready.\n";
				 */
				System.out.println("What would you like to do?");
				input = scone.nextLine();
				input = input.toUpperCase();
				switch (input) {
					case "EXIT" :{
						app.exit();
						running = false;
						break;
					}
					case "QUIT" :{
						app.exit();
						running = false;
						break;
					}
					case "HELP" :{
						System.out.println(app.getCommandList());
						break;
					}
					case "H" :{
						System.out.println(app.getCommandList());
						break;
					}
					case "SETUP" :{
						setup = true;
						break;
					}
					case "SEARCH" :{
						System.out.println(app.findIt(scone, input));
						break;
					}
					case "FIND" :{
						System.out.println(app.findIt(scone, input));
						break;
					}
					case "LOOK" :{
						System.out.println(app.findIt(scone, input));
						break;
					}
					case "ADD" :{
						app.addIt(scone, input);
						break;
					}
					case "LEFTOVER" :{
						System.out.println("Would you like to add or find leftovers?");
						input = scone.nextLine();
						if(input.toUpperCase().equals("ADD")) {
							app.addLeftovers(scone);
						}
						else {
							System.out.println(app.findLeftovers());
						}
						break;
					}
					case "LIST" :{
						System.out.println(app.printGroceryList());
						break;
					}
					case "REMOVE" :{
						app.removeFood(scone);
						break;
					}
					case "WARNINGS" :{
						app.printUpdates(System.currentTimeMillis());
						break;
					}
					case "EXPIRED" :{
						System.out.println(app.expiredFoods());
						System.out.println(app.addToListExpired(scone));
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

}
