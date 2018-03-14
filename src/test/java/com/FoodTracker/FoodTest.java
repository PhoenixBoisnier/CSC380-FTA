package test.java.com.FoodTracker;

import org.junit.*;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import main.java.com.FoodTracker.*;

public class FoodTest {
	  @Test public void testName() {
		  
		  Food cans = new Food("cans", 1.00, 100);
		  //abtCans is a food whose expiration is approaching the given time
		  Food abtCans = new Food("abtcans", 1.00, 100,
				  System.currentTimeMillis(), System.currentTimeMillis()+
				  (FoodTrackerApp.millisecondsInDay*2));
		  //expCans is a food whose expiration is past the time given
		  Food expCans = new Food("expcans", 1.00, 100, 
				  System.currentTimeMillis(), System.currentTimeMillis()-1);
		  Food fruit = new Food("fruit", 10.00, 5);
		  //abtFruit is a food whose expiration is past the given time
		  Food abtFruit = new Food("abtfruit", 10.00, 1,
				  System.currentTimeMillis(), System.currentTimeMillis()+
				  (FoodTrackerApp.millisecondsInDay*2));
		  //expFruit is a food whose expiration is past the time given
		  Food expFruit = new Food("expfruit", 10.00, 1,
				  System.currentTimeMillis(), System.currentTimeMillis()-1);
		  Food bread = new Food("bread", 2.00, 5);
		  //abtBread is a food whose expiration is approaching the given time
		  Food abtBread = new Food("abtbread", 2.00, 3,
				  System.currentTimeMillis(), System.currentTimeMillis()+
				  (FoodTrackerApp.millisecondsInDay*2));
		  //expBread is a food whose expiration is past the time given
		  Food expBread = new Food("expbread", 2.00, 3,
				  System.currentTimeMillis(), System.currentTimeMillis()-1);
		  
		  //This section tests is expired
		  assertFalse(cans.isExpired());
		  assertTrue(expCans.isExpired());
		  assertFalse(fruit.isExpired());
		  assertTrue(expFruit.isExpired());
		  assertFalse(bread.isExpired());
		  assertTrue(expBread.isExpired());
		  
		  //This section tests is about to expire
	/*	  final int THREEDAYS = 3;
		  assertFalse(cans.aboutToExpire(THREEDAYS));
		  assertTrue(abtCans.aboutToExpire(THREEDAYS));
		  assertFalse(fruit.aboutToExpire(THREEDAYS));
		  assertTrue(abtFruit.aboutToExpire(THREEDAYS));
		  assertFalse(bread.aboutToExpire(THREEDAYS));
		  assertTrue(abtBread.aboutToExpire(THREEDAYS)); */
		  
		  //This section tests save food method
		  String cansSaved = cans.getName()+"\n"+cans.getCost()+"\n"+
				  cans.privateDeets();
		  String fruitSaved = fruit.getName()+"\n"+fruit.getCost()+"\n"+
				  fruit.privateDeets();
		  String breadSaved = bread.getName()+"\n"+bread.getCost()+"\n"+
				  bread.privateDeets();
		  assertTrue(cans.saveFood().equals(cansSaved));
		  assertTrue(fruit.saveFood().equals(fruitSaved));
		  assertTrue(bread.saveFood().equals(breadSaved));
		  assertFalse(cans.saveFood().equals(""));
		  assertFalse(fruit.saveFood().equals(""));
		  assertFalse(bread.saveFood().equals(""));
	  }
	}