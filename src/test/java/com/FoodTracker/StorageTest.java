package test.java.com.FoodTracker;

import org.junit.*;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import main.java.com.FoodTracker.*;

/**
 * Test class for Storage class.
 * 
 * @author phoenix
 *
 */
public class StorageTest {

	  Storage tester = new Storage();
	  GroceryList testList = new GroceryList();
	  Food bacon = new Food("bacon", 8.00, 30);
	  Food frozenMeat = new Food("frozen meat", 8.00, 90);
	  Food cans = new Food("cans", 2.00, 100);
	  String empty = "This is the food currently present:\n"+
			  "\tFridge:\n"+
			  "\t\tEmpty.\n"+
			  "\tFreezer:\n"+
			  "\t\tEmpty.\n"+
			  "\tPantry:\n"+
			  "\t\tEmpty.\n";
	  
	  @Test public void toStringTest() {
		  
		  assertTrue(tester.toString().equals(empty));
		  
	  }
	  
	  @Test public void addFoodTest() {
		  
		  tester.AddFood(bacon, Mode.FRIDGE, testList);
		  tester.AddFood(frozenMeat, Mode.FREEZER, testList);
		  tester.AddFood(cans, Mode.PANTRY, testList);
		  assertFalse(tester.toString().equals(empty));
		  assertTrue(tester.getFridge().size()==1);
		  assertTrue(tester.getFreezer().size()==1);
		  assertTrue(tester.getPantry().size()==1);
		  
	  }
	  
	  @Test public void removeToGrocListTest() {
		  
		  tester.remove(bacon, Mode.FRIDGE, testList);
		  tester.remove(frozenMeat, Mode.FREEZER, testList);
		  tester.remove(cans, Mode.PANTRY, testList);
		  assertTrue(testList.getSize()==3);
		  assertTrue(tester.toString().equals(empty));
		  assertTrue(tester.getFreezer().size()==0);
		  assertTrue(tester.getFridge().size()==0);
		  assertTrue(tester.getPantry().size()==0);
		  
	  }
	}