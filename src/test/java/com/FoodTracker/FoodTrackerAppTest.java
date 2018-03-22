package test.java.com.FoodTracker;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import org.junit.Test;

import main.java.com.FoodTracker.FoodTrackerApp;

public class FoodTrackerAppTest {
	
	FoodTrackerApp testApp = new FoodTrackerApp();
	//TODO determine what "user" input will be 
	String testText = "";
	byte byteStream[] = testText.getBytes();
	ByteArrayInputStream input1 = new ByteArrayInputStream(byteStream); 
	Scanner scone = new Scanner(input1);

	@Test public void addItTest() {
		
		
		
	}
	
	@Test public void makeFoodTest() {
		
		
		
	}
}
