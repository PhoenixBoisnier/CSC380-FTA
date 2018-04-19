package main.java.com.FoodTracker;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
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
	 * -int: items in faves
	 * --String: food name
	 * --string: name of food
	 * --double: cost of food
	 * --int: days to expire
	 * --long: time at input
	 * --long: expiration time
	 * ---repeat above for number of items in location
	 * -int: days before warning
	 * -int: days to generate list
	 * -int: days to save in freezer
	 * -long: System time at save
	 * end of file
	 * 
 * This class reads and writes save files for the app.
 * See above for format. This class reads input, if any,
 * at creation. This class does not have an auto-save
 * method. To save data, call writeFile().
 * 
 * @author phoenix
 *
 */

public class FTAParser {
	
	private GroceryList list = new GroceryList();
	private Storage store = new Storage();
	private HashMap<String, Food> faves = new HashMap<>();
	private int daysWarning;
	private int listGenerate;
	private int freezerTime;
	private long prevTime;
	private int exists = 0;
	private final static String path = System.getProperty("user.home")+"/FTApp";
	private File input;
	private File dir = new File(path);
	private Scanner scone = null;
	private static BufferedWriter write = null;
	
	/**
	 * Creates a new parser for the input file and generates a Storage object
	 * and GroceryList object from the input file.
	 * @param l
	 * @param s
	 */
	public FTAParser(String s) {
		input = new File(path+s);
		//The input file should only not exist when the program is first run
		//on a new machine.
		if(!input.exists()) {
			dir.mkdirs();
			try {
				input.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//Otherwise, it should exist.
		else {
			try {
				scone = new Scanner(input);
				exists = 1;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			//This takes the input file and saves its data to the 
			//GroceryList l and the Storage s.
			this.readFile();
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
	 * Returns the favorites list.
	 * @return
	 */
	public HashMap<String,Food> getFaves() {
		return faves;
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
	 * Returns the leftover freezer time.
	 * @return
	 */
	public int getFreezerTime() {
		return freezerTime;
	}
	
	/**
	 * Gives system time at last save
	 * @return
	 */
	public long getBeginTime() {
		return prevTime;
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
	 * -int: number of items in faves
	 * --String: name of food
	 * --string: name of food
	 * --double: cost of food
	 * --int: days to expire
	 * --long: time at input
	 * --long: expiration time
	 * ---repeat above for number of items in location
	 * -int: days before warning
	 * -int: days to generate list
	 * -int: days to save in freezer
	 * -long: System time at save
	 * end of file
	 * 
	 * @param l
	 * @param s
	 */
	public void saveFile(GroceryList l, Storage s, HashMap<String,Food> fave,
			int warn, int gen, int frz, long bgn) {
		System.out.println("Saving info...");
		input.delete();
		input = new File(path+"/FTAsave.txt");
		try {
			write = new BufferedWriter(new FileWriter(input));
			System.out.println("Save-To file found...");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Save-To file not found.");
		}
		try {
			
			//This part writes each storage to file
				//For fridge
			System.out.println("Saving fridge info...");
			write.write("fridge\n");
			write.write(s.getFridge().size()+"\n");
			for(int q = 0; q<s.getFridge().size(); q++) {
				Food temp = s.getFridge().get(q);
				write.write(temp.saveFood());
			}
				//For freezer
			System.out.println("Saving freezer info...");
			write.write("freezer\n");
			write.write(s.getFreezer().size()+"\n");
			for(int q = 0; q<s.getFreezer().size(); q++) {
				Food temp = s.getFreezer().get(q);
				write.write(temp.saveFood());
			}
				//For pantry
			System.out.println("Saving pantry info...");
			write.write("pantry\n");
			write.write(s.getPantry().size()+"\n");
			for(int q = 0; q<s.getPantry().size(); q++) {
				Food temp = s.getPantry().get(q);
				write.write(temp.saveFood());
			}
			
			//This part writes the grocery list to file
			System.out.println("Saving list info...");
			write.write(l.getSize()+"\n");
			for(int q = 0; q<l.getSize(); q++) {
				Food temp = l.getFood(q);
				write.write(temp.saveFood());
			}
			Iterator<Entry<String, Food>> i = fave.entrySet().iterator();
			write.write(fave.size()+"\n");
			while (i.hasNext()) {
			    Map.Entry<String, Food> pair = 
			        (Map.Entry<String, Food>)i.next();
			    write.write(pair.getKey()+"\n");
			    write.write(pair.getValue().saveFood());
			    i.remove();
			    }
			
			//This part writes the settings to file
			System.out.println("Saving settings info...");
			write.write(warn+"\n");
			write.write(gen+"\n");
			write.write(frz+"\n");
			write.write(bgn+"\n");
			
			write.flush();
			
			System.out.println("Save complete.");
			
		} catch (IOException e) {
			System.out.println("Failed to write to save file.");
		}
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
	 * -int: number of items in faves
	 * --String: name of food
	 * --string: name of food
	 * --double: cost of food
	 * --int: days to expire
	 * --long: time at input
	 * --long: expiration time
	 * ---repeat above for number of items in location
	 * -int: days before warning
	 * -int: days to generate list
	 * -int: days to save in freezer
	 * -long: System time at save
	 * end of file
	 */
	private void readFile() {
		if(scone != null && scone.hasNextLine()) {
			//if the file exists, there will always be 3 locations
			//recorded to be read
			if(scone.nextLine().equals("fridge")) {
				int lim = Integer.parseInt(scone.nextLine());
				for(int q = 0; q<lim; q++) {
					String name = scone.nextLine();
					double cost = Double.parseDouble(scone.nextLine());
					int days = Integer.parseInt(scone.nextLine());
					long time = Long.parseLong(scone.nextLine());
					long expire = Long.parseLong(scone.nextLine());
					store.AddFood((new Food(name,cost,days,time,
							expire)),Mode.FRIDGE,list);
				}
			}
			if(scone.nextLine().equals("freezer")) {
				int lim = Integer.parseInt(scone.nextLine());
				for(int q = 0; q<lim; q++) {
					String name = scone.nextLine();
					double cost = Double.parseDouble(scone.nextLine());
					int days = Integer.parseInt(scone.nextLine());
					long time = Long.parseLong(scone.nextLine());
					long expire = Long.parseLong(scone.nextLine());
					store.AddFood((new Food(name,cost,days,time,
							expire)),Mode.FREEZER,list);
				}
			}
			if(scone.nextLine().equals("pantry")) {
				int lim = Integer.parseInt(scone.nextLine());
				for(int q = 0; q<lim; q++) {
					String name = scone.nextLine();
					double cost = Double.parseDouble(scone.nextLine());
					int days = Integer.parseInt(scone.nextLine());
					long time = Long.parseLong(scone.nextLine());
					long expire = Long.parseLong(scone.nextLine());
					store.AddFood((new Food(name,cost,days,time,
							expire)),Mode.PANTRY,list);
				}
			}
			//then the current grocery list is recorded and read
			int lim = Integer.parseInt(scone.nextLine());
			for(int i = 0; i<lim; i++) {
				String name = scone.nextLine();
				double cost = Double.parseDouble(scone.nextLine());
				int days = Integer.parseInt(scone.nextLine());
				long time = Long.parseLong(scone.nextLine());
				long expire = Long.parseLong(scone.nextLine());
				list.manualAdd(new Food(name,cost,days,time,expire));
			}
			//then the hash map info is read
			lim = Integer.parseInt(scone.nextLine());
			for(int i = 0; i<lim; i++) {
				String kname = scone.nextLine();
				String name = scone.nextLine();
				double cost = Double.parseDouble(scone.nextLine());
				int days = Integer.parseInt(scone.nextLine());
				long time = Long.parseLong(scone.nextLine());
				long expire = Long.parseLong(scone.nextLine());
				faves.put(kname, new Food(name,cost,days,time,expire));
			}
			//then we read in the saved settings.
			daysWarning = Integer.parseInt(scone.nextLine());
			listGenerate = Integer.parseInt(scone.nextLine());
			freezerTime = Integer.parseInt(scone.nextLine());
			prevTime = Long.parseLong(scone.nextLine());

		}
		//in the case that input file does not exist somehow
		else {
			System.out.println("Unable to load valid save file.");
			if(!scone.hasNextLine()) {
				//should only happen during first time setup
				System.out.println("File empty.");
				exists = 0;
			}
		}
	}
}
