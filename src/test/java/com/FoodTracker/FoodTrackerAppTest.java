package test.java.com.FoodTracker;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import org.junit.Test;

import main.java.com.FoodTracker.AddFoodException;
import main.java.com.FoodTracker.FoodTrackerApp;
import main.java.com.FoodTracker.GroceryList;
import main.java.com.FoodTracker.Storage;

public class FoodTrackerAppTest {
	
	Storage food = new Storage();
	GroceryList list = new GroceryList();
	FoodTrackerApp testApp = new FoodTrackerApp(list, food);
	//TODO determine what "user" input will be 
	
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
}
