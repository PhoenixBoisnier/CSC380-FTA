package main.java.com.FoodTracker;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

//TODO change format and methods for leftover settings
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
	 * -int: days before warning
	 * -int: days to generate list
	 * end of file
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
	private int daysWarning;
	private int listGenerate;
	private int exists = 0;
	//TODO fix the path
	private final String path = System.getProperty("user.home")+"/FTApp";
	File input = new File(path+"/save.txt");
	Scanner scone = null;
	BufferedWriter write = null;
	
	/**
	 * Creates a new parser for the input file and generates a Storage object
	 * and GroceryList object from the input file.
	 * @param l
	 * @param s
	 */
	public FTAParser(GroceryList l, Storage s) {
		list = l;
		store = s;
		
		//The input file should only not exist when the program is first run
		//on a new machine.
		if(!input.exists()) {
			try {
				input.createNewFile();
			} catch (IOException e1) {
				//TODO find out why save file is found
				System.out.println("Save file found.");
			}
		}
		//Otherwise, it should exist.
		else {
			//But because of the structure of scanners, we need
			//to use try catch anyway.
			try {
				scone = new Scanner(input);
				exists = 1;
			} catch (FileNotFoundException e) {
				System.out.println("Save file not found.");
			}
			//This takes the input file and saves its data to the 
			//GroceryList l and the Storage s.
			this.readFile(l, s);
		}
	}
	
	/**
	 * Returns the GroceryList object generated by the input file.
	 * @return
	 */
	public GroceryList getList() {
		return list;
	}
	
	/**
	 * Returns the Storage object generated by the input file.
	 * @return
	 */
	public Storage getStorage() {
		return store;
	}
	
	/**
	 * Returns the days until warning settings.
	 * @return
	 */
	public int getDaysWarning() {
		return daysWarning;
	}
	
	/**
	 * Returns the grocery list generation settings.
	 * @return
	 */
	public int getListGenerate() {
		return listGenerate;
	}
	
	/**
	 * Returns 1 if there was a save file found.
	 * @return
	 */
	public int getExists() {
		return exists;
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
	 * -int: days before warning
	 * -int: days to generate list
	 * end of file
	 * 
	 * @param l
	 * @param s
	 */
	public void saveFile(GroceryList l, Storage s, int warn, int gen) {
		File output = new File(path+"/save.txt");
		String outputting = "Begin File";
		try {
			write = new BufferedWriter(new FileWriter(output));
		} catch (IOException e) {
			//TODO find out why save-to file is not found
			System.out.println("Save-To file not found.");
		}
		try {
			
			//This part writes each storage to file
				//For fridge
			write.write("fridge\n");
			write.write(s.getFridge().size()+"\n");
			for(int q = 0; q<s.getFridge().size(); q++) {
				Food temp = s.getFridge().get(q);
				write.write(temp.saveFood());
			}
				//For freezer
			write.write("freezer\n");
			write.write(s.getFreezer().size()+"\n");
			for(int q = 0; q<s.getFreezer().size(); q++) {
				Food temp = s.getFreezer().get(q);
				write.write(temp.saveFood());
			}
				//For pantry
			write.write("pantry\n");
			write.write(s.getPantry().size()+"\n");
			for(int q = 0; q<s.getPantry().size(); q++) {
				Food temp = s.getPantry().get(q);
				write.write(temp.saveFood());
			}
			
			//This part writes the grocery list to file
			write.write(l.getSize()+"\n");
			for(int q = 0; q<l.getSize(); q++) {
				Food temp = l.getFood(q);
				write.write(temp.saveFood());
			}
			
			//This part writes the settings to file
			write.write(warn+"\n");
			write.write(gen+"\n");
			
		} catch (IOException e) {
			System.out.println("Failed to write to save file.");
			System.out.println("Failed at "+outputting+".");
		}
		
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
	 * -int: days before warning
	 * -int: days to generate list
	 * end of file
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
			
			//then we read in the saved settings.
			daysWarning = Integer.parseInt(scone.nextLine());
			listGenerate = Integer.parseInt(scone.nextLine());

		}
		//in the case that input file does not exist somehow
		else {
			System.out.println("Unable to load valid save file.");
		}
	}
}
