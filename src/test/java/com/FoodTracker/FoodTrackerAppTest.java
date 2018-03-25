package test.java.com.FoodTracker;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import org.junit.Test;

import main.java.com.FoodTracker.AddFoodException;
import main.java.com.FoodTracker.Food;
import main.java.com.FoodTracker.FoodTrackerApp;
import main.java.com.FoodTracker.GroceryList;
import main.java.com.FoodTracker.Mode;
import main.java.com.FoodTracker.Storage;

public class FoodTrackerAppTest {
	
	Storage food = new Storage();
	GroceryList list = new GroceryList();
	FoodTrackerApp testApp = new FoodTrackerApp(list, food);
	
	public Scanner makeInput(String userInput) {
		
		String testText = userInput;
		byte byteStream[] = testText.getBytes();
		ByteArrayInputStream input1 = new ByteArrayInputStream(byteStream); 
		Scanner scone = new Scanner(input1);
		return scone;
		
	}
	
	
	@Test public void makeFoodTest() {
		
		String makeFoodTestInput = "apple\n2.0\n30\ny\n";
		Scanner scone = makeInput(makeFoodTestInput);
		
		try {
			assertTrue(testApp.makeFood(scone).getName().equals("apple"));
		} catch (AddFoodException e) {
			assert(false);
		}
		
	}
	
	@Test public void addItTest() {
		
		String addItTestInput = "fridge\napple\n2.0\n30\ny\n";
		Scanner scone = makeInput(addItTestInput);
		String input = "";
		testApp.addIt(scone, input);
		
		assertTrue(food.getFridge().size()==1);
		assertFalse(food.getFreezer().size()==1);
		assertFalse(food.getPantry().size()==1);
		
	}
	
	@Test public void findItTest1() {
		
		String addItTestInput = "fridge\napple\n2.0\n30\ny\n";
		Scanner scone = makeInput(addItTestInput);
		String input = "";
		testApp.addIt(scone, input);
		
		String findItTestInput = "fridge\napple\n";
		scone = makeInput(findItTestInput);
		input = "";
		String findItResults = testApp.findIt(scone, input);
		assertTrue(findItResults.equals("apple was found in the fridge."));
				
	}
	
	@Test public void findItTest2() {
		
		String addItTestInput = "fridge\napple\n2.0\n30\ny\n";
		Scanner scone = makeInput(addItTestInput);
		String input = "";
		testApp.addIt(scone, input);
		
		String findItTestInput = "freezer\napple\n";
		scone = makeInput(findItTestInput);
		input = "";
		String foundApple = "apple was found in the fridge.";
		assertFalse(testApp.findIt(scone, input).equals(foundApple));
		
	}
	
	@Test public void findItTest3() {
		
		String addItTestInput = "fridge\napple\n2.0\n30\ny\n";
		Scanner scone = makeInput(addItTestInput);
		String input = "";
		testApp.addIt(scone, input);
		
		String findItTestInput = "q\napple\n";
		scone = makeInput(findItTestInput);
		input = "";
		String foundApple = "apple was found.";
		assertTrue(testApp.findIt(scone, input).equals(foundApple));
		
	}
	
	@Test public void removeTest() {
		
		String addItTestInput = "fridge\napple\n2.0\n30\ny\n";
		Scanner scone = makeInput(addItTestInput);
		String input = "";
		testApp.addIt(scone, input);
		
		String removeItInput = "fridge\napple\n";
		scone = makeInput(removeItInput);
		input = "";
		testApp.removeFood(scone);
		
		assertTrue(food.getFridge().size()==0);
		assertTrue(list.getSize()==1);
		
	}
	
	@Test public void addLeftOverTest() {
		
		String addLeftoversTestInput = "chicken soup\nn\n";
		Scanner scone = makeInput(addLeftoversTestInput);
		
		food.addLeftover(scone);
		assertTrue(food.getFridge().size()==1);
		assertTrue(food.getFridge().get(0).isLeftover());
		assertFalse(food.getFreezer().size()==1);
		
	}
	
	@Test public void findLeftoversTest() {
		
		String findLeftoversTestInput = "fridge\napple\n2.0\n30\ny\n";
		Scanner scone = makeInput(findLeftoversTestInput);
		
		testApp.addLeftovers(scone);
		
		String value = "These are the leftovers present:\n"
				+"\t"+food.getFridge().get(0).getName()+" in fridge";
		
		assertTrue(testApp.findLeftovers().equals(value));
		
	}
	
	@Test public void addToListExpiredTest1() {
		
		String addExpiredTest = "1\n";
		Scanner scone = makeInput(addExpiredTest);
		
		food = new Storage();
		food.AddFood(new Food("Expired meat", 10.00, 1, 
					System.currentTimeMillis(), System.currentTimeMillis()-1),
				Mode.FRIDGE, list);
		testApp = new FoodTrackerApp(list, food);
		
		String value = "Added expired foods to grocery list."
				+"\nExpired foods removed from storage.";
		
		String printVal = "It's time to go shopping. Here is "
				+ "your grocery list:\n";
		printVal+="Expired meat, cost: 10.0\n";
		
		assertTrue(testApp.addToListExpired(scone).equals(value));
		assertTrue(testApp.printGroceryList().equals(printVal));
		
	}
	
	@Test public void addToListExpiredTest2() {
		
		String addExpiredTest = "2\n";
		Scanner scone = makeInput(addExpiredTest);
		
		food = new Storage();
		food.AddFood(new Food("Expired meat", 10.00, 1, 
					System.currentTimeMillis(), System.currentTimeMillis()-1),
				Mode.FRIDGE, list);
		testApp = new FoodTrackerApp(list, food);
		
		String value = "Expired foods only removed from storage.";
		
		String printVal = "It's time to go shopping. Here is "
				+ "your grocery list:\n"
				+"You don't have anything in your list.";
		
		assertTrue(testApp.addToListExpired(scone).equals(value));
		assertTrue(testApp.printGroceryList().equals(printVal));
	}
	
	@Test public void addToListExpiredTest3() {
		
		String addExpiredTest = "3\n";
		Scanner scone = makeInput(addExpiredTest);
		
		food = new Storage();
		food.AddFood(new Food("Expired meat", 10.00, 1, 
					System.currentTimeMillis(), System.currentTimeMillis()-1),
				Mode.FRIDGE, list);
		testApp = new FoodTrackerApp(list, food);
		
		String value = "No action performed.";
		
		String printVal = "It's time to go shopping. Here is "
				+ "your grocery list:\n"
				+"You don't have anything in your list.";
		
		assertTrue(testApp.addToListExpired(scone).equals(value));
		assertTrue(testApp.printGroceryList().equals(printVal));
		
	}
	
	@Test public void printGroceryListTest() {
		
		list.manualAdd(new Food("Meat", 10.0, 1));
		testApp = new FoodTrackerApp(list, new Storage());
		String printVal = "It's time to go shopping. Here is "
				+ "your grocery list:\n";
		printVal+="Meat, cost: 10.0\n";
		
		assertTrue(testApp.printGroceryList().equals(printVal));
		
	}
	
	@Test public void expiredFoodsTest() {
		
		food = new Storage();
		food.AddFood(new Food("Expired meat", 10.00, 1, 
					System.currentTimeMillis(), System.currentTimeMillis()-1),
				Mode.FRIDGE, list);
		testApp = new FoodTrackerApp(list, food);
		
		String printVal = "Expired foods:\n"
				+"\tExpired meat\n";
		
		assertTrue(testApp.expiredFoods().equals(printVal));
		
	}
	
	//TODO printCloseToExpiringTest
	//TODO printUpdatesTest
	//TODO exitTest
}
