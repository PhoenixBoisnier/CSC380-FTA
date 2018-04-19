package test.java.com.FoodTracker;

import org.junit.*;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.HashMap;

import static org.junit.Assert.assertFalse;
import main.java.com.FoodTracker.*;

public class FTAParserTest {
	
	  @Test public void readFile() {
		  FTAParser p = new FTAParser("/Test.txt");
		  assertTrue(p.getFaves()!=null);
		  assertFalse(p.getExists()==0);
		  assertTrue(p.getList()!=null);
		  assertTrue(p.getStorage()!=null);
	  }
	  
	  @Test public void writeFile() {
		  FTAParser p = new FTAParser("/Test.txt");
		  p.saveFile(new GroceryList(), new Storage(), new HashMap<String,Food>(),
				 3, 7, 30, System.currentTimeMillis());
		  File f = new File(System.getProperty("user.home")+"/FTApp/Test.txt");
		  assertTrue(f.exists());
	  }
	}