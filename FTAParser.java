package com.FridgeAlert;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

/**
 *
	 * File format is as follows:
	 * -string: storage location
	 * -int: number of items in location
	 * --string: name of food
	 * --double: cost of food
	 * --int: days to expire
	 * --long: time at input
	 * --long: expiration time
	 * ---repeat above for number of items in location
	 * ----repeat above two more times
	 * -int: number of items in grocery list
	 * --string: name of food
	 * --double: cost of food
	 * --int: days to expire
	 * --long: time at input
	 * --long: expiration time
	 * ---repeat above for number of items in location
	 * .
 * This class reads and writes save files for the app.
 * See above for format. This class reads input, if any,
 * at creation. This class does not have an auto-save
 * method. To save data, call writeFile().
 * 
 * @author phoenix
 *
 */
public class FTAParser {
	
	private GroceryList list;
	private Storage store;
	private final String path = System.getProperty("user.home")+"/FTApp/save.txt";
	File input = new File(path);
	Scanner scone = null;
	FileWriter write;
	
	public FTAParser(GroceryList l, Storage s) {
		list = l;
		store = s;
		
		if(!input.exists()) {
		    try{
		        input.mkdir();
		    } 
		    catch(SecurityException e) {
		    	
		    }
		}
		else {
			try {
				scone = new Scanner(input);
			} catch (FileNotFoundException e) {
				System.out.println("Save file not found.");
			}
			this.readFile(l, s);
		}
	}
	
	public GroceryList getList() {
		return list;
	}
	
	public Storage getStorage() {
		return store;
	}
	
	/**
	 * File format is as follows:
	 * -string: storage location
	 * -int: number of items in location
	 * --string: name of food
	 * --double: cost of food
	 * --int: days to expire
	 * --long: time at input
	 * --long: expiration time
	 * ---repeat above for number of items in location
	 * ----repeat above two more times
	 * -int: number of items in grocery list
	 * --string: name of food
	 * --double: cost of food
	 * --int: days to expire
	 * --long: time at input
	 * --long: expiration time
	 * ---repeat above for number of items in location
	 * 
	 * @param l
	 * @param s
	 */
	public void saveFile(GroceryList l, Storage s) {
		File output = new File(path);
		//TODO figure out how to write files
		//TODO write to output
		input = output;
	}
	
	/**
	 * File format is as follows:
	 * -string: storage location
	 * -int: number of items in location
	 * --string: name of food
	 * --double: cost of food
	 * --int: days to expire
	 * --long: time at input
	 * --long: expiration time
	 * ---repeat above for number of items in location
	 * ----repeat above two more times
	 * -int: number of items in grocery list
	 * --string: name of food
	 * --double: cost of food
	 * --int: days to expire
	 * --long: time at input
	 * --long: expiration time
	 * ---repeat above for number of items in location
	 * 
	 * @param l
	 * @param s
	 */
	public void readFile(GroceryList l, Storage s) {
		if(scone != null) {
			//if the file exists, there will always be 3 locations
			//recorded to be read
			for(int i = 0; i<3; i++) {
				String storage = scone.nextLine();
				int items = Integer.parseInt(scone.nextLine());
				for(int a = 0; a<items; a++) {
					switch (storage) {
						case "fridge": {
							String name = scone.nextLine();
							double cost = Double.parseDouble(scone.nextLine());
							int days = Integer.parseInt(scone.nextLine());
							long time = Long.parseLong(scone.nextLine());
							long expire = Long.parseLong(scone.nextLine());
							s.AddFood((new Food(name,cost,days,time,
									expire)),Mode.FRIDGE,l);
						}
						case "freezer": {
							String name = scone.nextLine();
							double cost = Double.parseDouble(scone.nextLine());
							int days = Integer.parseInt(scone.nextLine());
							long time = Long.parseLong(scone.nextLine());
							long expire = Long.parseLong(scone.nextLine());
							s.AddFood((new Food(name,cost,days,time,
									expire)),Mode.FREEZER,l);
						}
						default : {
							String name = scone.nextLine();
							double cost = Double.parseDouble(scone.nextLine());
							int days = Integer.parseInt(scone.nextLine());
							long time = Long.parseLong(scone.nextLine());
							long expire = Long.parseLong(scone.nextLine());
							s.AddFood((new Food(name,cost,days,time,
									expire)),Mode.PANTRY,l);
						}
					}
				}
			}
			//then the current grocery list is recorded and read
			for(int i = 0; i<Integer.parseInt(scone.nextLine()); i++) {
				String name = scone.nextLine();
				double cost = Double.parseDouble(scone.nextLine());
				int days = Integer.parseInt(scone.nextLine());
				long time = Long.parseLong(scone.nextLine());
				long expire = Long.parseLong(scone.nextLine());
				l.manualAdd(new Food(name,cost,days,time,expire));
			}
		}
		//in the case that input file does not exist somehow
		else {
			System.out.println("Unable to load valid save file.");
		}
	}
}
