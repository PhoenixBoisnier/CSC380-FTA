package com.FridgeAlert;
/**
 * The main item in the program, stores all the info about the food items.
 * WHen food is constructed, it takes the time of the system at creation 
 * and stores it in food. This is compared to the system time when about
 * to expire is called and is compared with the time given to main as the 
 * preference. Words.
 * 
 * @author phoenix
 *
 */
public class Food {
	
	public long getExpiringAt() {
		return 0;
	}

	public double getCost() {
		return 1.0;
	}
	
	public String getName() {
		return "Food";
	}
	
	/**
	 * This method should take the waiting time and see if the time from 
	 * insert is "t" days away from the expiration time.
	 * @param t
	 * @return
	 */
	public boolean aboutToExpire(int t) {
		//TODO checks insert time with System current time and compares the
		//difference to the value t
		if(true) {
			return true;
		}
		else return false;
	}

}
