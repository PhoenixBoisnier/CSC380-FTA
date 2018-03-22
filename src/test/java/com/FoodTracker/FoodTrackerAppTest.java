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

@SuppressWarnings("static-access")
public class FoodTrackerAppTest {
	
	FoodTrackerApp testApp = new FoodTrackerApp();
	Storage food = new Storage();
	GroceryList list = new GroceryList();
	//TODO determine what "user" input will be 
	
	
	@Test public void makeFoodTest() {
		
		String makeFoodTestInput = "apple\n2.0\n30\ny\n";
		Scanner scone = FoodTrackerAppTest.makeInput(makeFoodTestInput);
		
		try {
			assertTrue(testApp.makeFood(scone).getName().equals("apple"));
		} catch (AddFoodException e) {
			assert(false);
		}
		
	}
	
	@Test public void addItTest() {
		
		String addItTestInput = "fridge\napple\n2.0\n30\ny\n";
		Scanner scone = FoodTrackerAppTest.makeInput(addItTestInput);
		String input = "";
		
		testApp.addIt(scone, input, food, list);
		assertTrue(food.getFridge().size()==1);
		assertFalse(food.getFreezer().size()==1);
		assertFalse(food.getPantry().size()==1);
		
	}
	
	public static Scanner makeInput(String userInput) {
		
		String testText = userInput;
		byte byteStream[] = testText.getBytes();
		ByteArrayInputStream input1 = new ByteArrayInputStream(byteStream); 
		Scanner scone = new Scanner(input1);
		return scone;
		
	}
}
