package test.java.com.FoodTracker;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import main.java.com.FoodTracker.Leftover;

@SuppressWarnings("static-access")
public class LeftoverTest {

	boolean frozen = true;
	Leftover lTest = new Leftover("soup", !frozen);
	Leftover flTest = new Leftover("bacon", frozen);
	
	@Test public void isLeftoverTest() {
		assertTrue(lTest.isLeftover);
		assertTrue(flTest.isLeftover);
	}
	
	@Test public void durationTest() {
		assertTrue(lTest.ifFrozen(!frozen)==3);
		assertFalse(flTest.ifFrozen(frozen)==3);
		assertTrue(flTest.ifFrozen(frozen)==30);
		Leftover.setFrozenDuration(10);
		assertFalse(flTest.ifFrozen(frozen)==30);
		assertTrue(flTest.ifFrozen(frozen)==10);
		assertTrue(lTest.ifFrozen(!frozen)==3);
	}
}
