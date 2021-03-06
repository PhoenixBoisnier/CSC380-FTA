package main.java.com.FoodTracker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class FTA_CmdRunner {

	public static void main(String[] args) {
		
		FTAParser p = new FTAParser("/FTAsave.txt");
		Scanner scone = new Scanner(System.in);
		FoodTrackerApp app = new FoodTrackerApp(p);
		
		String adminPowers = "advance: asks for food name and makes it about"
				+ " to expire.\n"
				+ "expire: expires all the food in the storage.";
		
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
				System.out.println(app.printUpdates());
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
				System.out.println("Sometimes, you put leftovers into the freezer.");
				System.out.println("How long do you want to keep these in the freezer?");
				System.out.println("This is a number of days.");
				num = -1;
				while(num<0) {
					try {
						num = Integer.parseInt(scone.nextLine());
					}
					catch(NumberFormatException e) {
						System.out.println("That was not a valid entry.");
						System.out.println("For now, we'll set it to 30.");
						System.out.println("This can be changed at a later"
								+ " time.");
						num = 30;
					}
					app.setFreezerTime(num);
				}
				app.setBeginTime();
				System.out.println("\nAwesome! Your grocery list will be "
						+ "provided every "+app.getGrocGenerate()+" days.");
				System.out.println("Alright, that looks like everything for"
						+ " now. Remember to change these setting as needed.");
				System.out.println("If at any point you need information about"
						+ "what commands you can use,\ntype 'help' or 'h' and "
						+ "press enter.");
				setup = false;
			}
			
			//First interaction with the user after setup
			while(!input.equals("EXIT")&&setup==false) {
				//Secondary loop where input commands are given.
				/*
				done - "add: asks for info about food to be stored.\n"
				done - "display: asks for display type, then displays all food.\n"
			 	done - "empty: empties all storage locations. Cannot be undone.\n"
			 	done - "empty list: clears grocery list. Cannot be undone.\n"
				done - "exit: saves data and exits the program.\n"
				done - "expired: lists all expired foods then asks the user\n"
				done - "if they would like them added to the grocery list and \n"
				done - "removed from their locations, only removed, or left alone."
				done - "favorite: asks for a food name and displays its info if found.\n"
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
					case "ADMINPOWERS" :{
						System.out.println(adminPowers);
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
					case "EMPTY" :{
						System.out.println("You are attempting to empty the storage.");
						System.out.println("Are you sure you want to do this? y/n");
						String yn = scone.nextLine();
						if(yn.toUpperCase().equals("Y")) {
							System.out.println("You selected yes. This action cannot be"
									+ "undone. Continues? y/n");
							yn = scone.nextLine();
							if(yn.toUpperCase().equals("Y")) {
								app.resetAllFoodStorage(yn.toUpperCase());
							}
						}
						break;
					}
					case "EMPTY LIST" :{
						System.out.println("You are attempting to clear the list.");
						System.out.println("Are you sure you want to do this? y/n");
						String yn = scone.nextLine();
						if(yn.toUpperCase().equals("Y")) {
							System.out.println("You selected yes. This action cannot be"
									+ "undone. Continues? y/n");
							yn = scone.nextLine();
							if(yn.toUpperCase().equals("Y")) {
								app.testMethod1(app.getFoods(), new GroceryList());
							}
						}
						break;
					}
					case "EMPTYLIST" :{
						System.out.println("You are attempting to clear the list.");
						System.out.println("Are you sure you want to do this? y/n");
						String yn = scone.nextLine();
						if(yn.toUpperCase().equals("Y")) {
							System.out.println("You selected yes. This action cannot be"
									+ "undone. Continues? y/n");
							yn = scone.nextLine();
							if(yn.toUpperCase().equals("Y")) {
								app.testMethod1(app.getFoods(), new GroceryList());
							}
						}
						break;
					}
					case "WARNINGS" :{
						System.out.println(app.printUpdates());
						break;
					}
					case "EXPIRED" :{
						String expiredResult = app.expiredFoods();
						System.out.println(expiredResult);
						if(!expiredResult.equals("No foods are expired.")) {
							System.out.println(app.addToListExpired(scone));
						}
						break;
					}
					case "FAVORITE" :{
						System.out.println("Please input food name.");
						String foodName = scone.nextLine();
						try {
							Food f = app.findFave(foodName);
							System.out.println("This is the food's information.");
							System.out.println(f);
						}
						catch (FavoriteFoodException e) {
							System.out.println("This food was not found.");
						}
						break;
					}
					case "DISPLAY" :{
						System.out.println("Would you like to display food by:(1,2,3)");
						System.out.println("\t1: Storage");
						System.out.println("\t2: Alphabetically");
						System.out.println("\t3: Expiration");
						int num = -1;
						ArrayList<Food> allFoods = new ArrayList<>();
						for(int i = 0; i<app.getFoods().getFreezer().size(); i++) {
							allFoods.add(app.getFoods().getFreezer().get(i));
						}
						for(int i = 0; i<app.getFoods().getFridge().size(); i++) {
							allFoods.add(app.getFoods().getFridge().get(i));
						}
						for(int i = 0; i<app.getFoods().getPantry().size(); i++) {
							allFoods.add(app.getFoods().getPantry().get(i));
						}
						try {
							num = Integer.parseInt(scone.nextLine());
						}
						catch (NumberFormatException e) {
							System.out.println("Not a valid entry. Displaying by storage.");
						}
						switch(num) {
							case 2 :{
								Food.byName = true;
								Collections.sort(allFoods);
								for(int i = 0; i<allFoods.size(); i++) {
									System.out.println(allFoods.get(i).getName());
								}
								break;
							}
							case 3 :{
								Food.byName = false;
								Collections.sort(allFoods);
								for(int i = 0; i<allFoods.size(); i++) {
									System.out.println(allFoods.get(i).getName());
								}
								break;
							}
							default :{
								for(int i = 0; i<allFoods.size(); i++) {
									System.out.println(allFoods.get(i).getName());
								}
							}
						}
						break;
					}
//---------------------------------here be cheat codes-------------------------------
					case "ADVANCE" :{
						System.out.println("Where is the food?");
						String where = scone.nextLine();
						System.out.println("Food name?:");
						String foodN = scone.nextLine();
						int aboutToExpire = app.getWarningTime()-1;
						Storage s = app.getFoods();
						switch (where.toUpperCase()) {
							case  "FREEZER" :{
								for(int i = 0; i<s.getFreezer().size(); i++) {
									if(s.getFreezer().get(i).getName().equals(foodN)) {
										Food fo = s.getFreezer().get(i);
										Food f = new Food(
												fo.getName(),
												fo.getCost(),
												-aboutToExpire
												);
										s.getFreezer().set(i, f);
									}
								}
								break;
							}
							case "PANTRY" :{
								for(int i = 0; i<s.getPantry().size(); i++) {
									if(s.getPantry().get(i).getName().equals(foodN)) {
										Food fo = s.getPantry().get(i);
										Food f = new Food(
												fo.getName(),
												fo.getCost(),
												-aboutToExpire
												);
										s.getPantry().set(i, f);
									}
								}
								break;
							}
							default :{
								for(int i = 0; i<s.getFridge().size(); i++) {
									if(s.getFridge().get(i).getName().equals(foodN)) {
										Food fo = s.getFridge().get(i);
										Food f = new Food(
												fo.getName(),
												fo.getCost(),
												-aboutToExpire
												);
										s.getFridge().set(i, f);
									}
								}
								break;
							}
						}
						break;
					}
					case "EXPIRE" :{
						
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
