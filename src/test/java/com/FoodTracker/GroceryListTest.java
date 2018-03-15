package test.java.com.FoodTracker;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import org.junit.*;
import main.java.com.FoodTracker.*;

/**
 * This tests the GroceryList class.
 * 
 * @author phoenix
 *
 */
public class GroceryListTest {
	  @Test public void testName() {
		  
		  GroceryList testList = new GroceryList();
		  
		  //Testing manual add
		  testList.manualAdd(new Food(null, 0, 0));
		  assertTrue(testList.getSize()==1);
		  
		  //Testing auto add
		  testList = new GroceryList();
		  ArrayList<Food> foods = new ArrayList<>();
		  foods.add(new Food(null, 0, 0));
		  foods.add(new Food(null, 0, 0));
		  testList.autoAdd(foods);
		  assertTrue(testList.getSize()==2);
		  
		  //Testing to String
		  testList = new GroceryList();
		  assertTrue(testList.toString().equals("You don't have anything in your list."));
		  testList.manualAdd(new Food("Muffins", 2.00, 3));
		  assertFalse(testList.toString().equals("You don't have anything in your list."));
		  assertTrue(testList.toString().equals(testList.getFood(0).getName()+
				  ", cost: "+testList.getFood(0).getCost()+"\n"));
		  
		  //Testing check inventory and remove item
		  testList = new GroceryList();
		  foods = new ArrayList<>();
		  Food fruit = new Food("fruit", 10.00, 1);
		  Food cans = new Food("cans", 1.00, 100);
		  Food bread = new Food("bread", 2.00, 3);
		  foods.add(fruit);
		  foods.add(bread);
		  testList.autoAdd(foods);
		  testList.manualAdd(cans);
		  assertTrue(testList.getSize()==3);
		  testList.checkInventory(foods);
		  assertTrue(testList.getSize()==1);
		  assertTrue(testList.getFood(0).getName().equals(cans.getName()));
		  testList.removeItem(cans.getName());
		  assertTrue(testList.getSize()==0);
	  }
	}