package test.java.com.FoodTracker;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import org.junit.Test;

import main.java.com.FoodTracker.AddFoodException;
import main.java.com.FoodTracker.FTAParser;
import main.java.com.FoodTracker.FavoriteFoodException;
import main.java.com.FoodTracker.Food;
import main.java.com.FoodTracker.FoodTrackerApp;
import main.java.com.FoodTracker.GroceryList;
import main.java.com.FoodTracker.Mode;
import main.java.com.FoodTracker.Storage;

public class FoodTrackerAppTest {
	
	FTAParser p = new FTAParser("/Test.txt");
	FoodTrackerApp testApp = new FoodTrackerApp(p);
	
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
		testApp.testMethod1(new Storage(), new GroceryList());
		testApp.addIt(scone, input);
		
		assertTrue(testApp.getFoods().getFridge().size()==1);
		assertFalse(testApp.getFoods().getFreezer().size()==1);
		assertFalse(testApp.getFoods().getPantry().size()==1);
		
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
		testApp.testMethod1(new Storage(), new GroceryList());
		testApp.addIt(scone, input);
		
		String removeItInput = "fridge\napple\n";
		scone = makeInput(removeItInput);
		input = "";
		testApp.removeFood(scone);
		
		assertTrue(testApp.getFoods().getFridge().size()==0);
		assertTrue(testApp.getList().getSize()==1);
		
	}
	
	@Test public void addLeftOverTest() {
		
		String addLeftoversTestInput = "chicken soup\nn\n";
		Scanner scone = makeInput(addLeftoversTestInput);
		testApp.testMethod1(new Storage(), new GroceryList());
		testApp.getFoods().addLeftover(scone);
		
		assertTrue(testApp.getFoods().getFridge().size()==1);
		assertTrue(testApp.getFoods().getFridge().get(0).isLeftover());
		assertFalse(testApp.getFoods().getFreezer().size()==1);
		
	}
	
	@Test public void findLeftoversTest() {
		
		String findLeftoversTestInput = "fridge\napple\n2.0\n30\ny\n";
		Scanner scone = makeInput(findLeftoversTestInput);
		testApp.testMethod1(new Storage(), new GroceryList());
		testApp.addLeftovers(scone);
		
		String value = "These are the leftovers present:\n"
				+"\t"+testApp.getFoods().getFridge().get(0).getName()+" in fridge";
		
		assertTrue(testApp.findLeftovers().equals(value));
		
	}
	
	@Test public void addToListExpiredTest1() {
		
		String addExpiredTest = "1\n";
		Scanner scone = makeInput(addExpiredTest);
		
		Storage food = new Storage();
		GroceryList list = new GroceryList();
		food.AddFood(new Food("Expired meat", 10.00, 1, 
					System.currentTimeMillis(), System.currentTimeMillis()-1),
				Mode.FRIDGE, list);
		testApp = new FoodTrackerApp(p);
		testApp.testMethod1(food, list);
		
		String value = "Added expired foods to grocery list."
				+"\nExpired foods removed from storage.";
		
		String printVal = "It's time to go shopping. Here is "
				+ "your grocery list:\n";
		printVal+="\tExpired meat, cost: 10.0\n\t"
				+"\nTotal cost is: "+10.0+"\n";
		
		assertTrue(testApp.addToListExpired(scone).equals(value));
		assertTrue(testApp.printGroceryList().equals(printVal));
		
	}
	
	@Test public void addToListExpiredTest2() {
		
		String addExpiredTest = "2\n";
		Scanner scone = makeInput(addExpiredTest);
		
		Storage food = new Storage();
		GroceryList list = new GroceryList();
		food.AddFood(new Food("Expired meat", 10.00, 1, 
					System.currentTimeMillis(), System.currentTimeMillis()-1),
				Mode.FRIDGE, list);
		testApp = new FoodTrackerApp(p);
		testApp.testMethod1(food, list);
		
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
		
		Storage food = new Storage();
		GroceryList list = new GroceryList();
		food.AddFood(new Food("Expired meat", 10.00, 1, 
					System.currentTimeMillis(), System.currentTimeMillis()-1),
				Mode.FRIDGE, list);
		testApp = new FoodTrackerApp(p);
		testApp.testMethod1(food, list);
		
		String value = "No action performed.";
		
		String printVal = "It's time to go shopping. Here is "
				+ "your grocery list:\n"
				+"You don't have anything in your list.";
		
		assertTrue(testApp.addToListExpired(scone).equals(value));
		assertTrue(testApp.printGroceryList().equals(printVal));
		
	}
	
	@Test public void printGroceryListTest() {
		
		Storage food = new Storage();
		GroceryList list = new GroceryList();
		list.manualAdd(new Food("Meat", 10.0, 1));
		testApp = new FoodTrackerApp(p);
		testApp.testMethod1(food, list);
		String printVal = "It's time to go shopping. Here is "
				+ "your grocery list:\n";
		printVal+="\tMeat, cost: 10.0\n\t"
				+ "\nTotal cost is: "+10.0+"\n";
		
		assertTrue(testApp.printGroceryList().equals(printVal));
		
	}
	
	@Test public void expiredFoodsTest() {
		
		Storage food = new Storage();
		GroceryList list = new GroceryList();
		food = new Storage();
		food.AddFood(new Food("Expired meat", 10.00, 1, 
					System.currentTimeMillis(), System.currentTimeMillis()-1),
				Mode.FRIDGE, list);
		testApp = new FoodTrackerApp(p);
		testApp.testMethod1(food, list);
		
		String printVal = "Expired foods:\n"
				+"\tExpired meat\n";
		
		assertTrue(testApp.expiredFoods().equals(printVal));
		
	}
	
	@Test public void favoritesTest() {
		String favoritesTestInput = "fridge\napple\n2.0\n30\ny\n";
		Scanner scone = makeInput(favoritesTestInput);
		testApp.testMethod1(new Storage(), new GroceryList());
		testApp.addIt(scone,"");
		
		favoritesTestInput = "fridge\napple\n";
		scone = makeInput(favoritesTestInput);
		testApp.removeFood(scone);
				
		favoritesTestInput = "fridge\napple\ny\n";
		scone = makeInput(favoritesTestInput);
		testApp.addIt(scone,"");
		
		assertTrue(testApp.getFoods().getFridge().get(0).getName().equals("apple"));
		assertTrue(testApp.getFoods().getFridge().get(0).getCost()==2.0);
	}
	
	@Test public void favoritesRetrievalTest() {
		String favoritesTestInput = "fridge\napple\n2.0\n30\ny\n";
		Scanner scone = makeInput(favoritesTestInput);
		testApp.testMethod1(new Storage(), new GroceryList());
		testApp.addIt(scone,"");
		
		favoritesTestInput = "fridge\napple\n";
		scone = makeInput(favoritesTestInput);
		testApp.removeFood(scone);
		
		try {
			assertFalse(testApp.findFave("apple")==null);
		} catch (FavoriteFoodException e) {
			e.printStackTrace();
		}
	}
	
	@Test public void closeToExpiringTest() {
		testApp.testMethod1(new Storage(), new GroceryList());
		String result = testApp.printCloseToExpiring();
		
		assertTrue(result.equals("Foods about to expire:\n"+
				"\tNo foods are close to expiring.\n"));
		
		String closeCheese = "fridge\ncheese\n2.0\n1\ny\n";
		Scanner scone = makeInput(closeCheese);
		testApp.addIt(scone, "");
		
		//Apparently, the test happens too fast to register that the cheese 
		//is, in fact, close to expiring
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String result2 = testApp.printCloseToExpiring();
		assertTrue(result2.equals("Foods about to expire:\n"+
				"\t"+"cheese"+"\n"));
	}
}
