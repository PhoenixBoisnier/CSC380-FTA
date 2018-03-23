package main.java.com.FoodTracker;

/**
 * 
 * A class used to quickly input leftover food into the storage locations.
 * 
 * @author phoenix
 *
 */
public class Leftover extends Food{

	private static int duration = 30;
	
	/**
	 * This constructor sets the default expiration of leftovers to 3 days
	 * and the cost to 0.
	 * @param name
	 * @param cost
	 * @param daysToExpire
	 */
	public Leftover(String name, boolean frozen) {
		super(name, 0.0, ifFrozen(frozen));
	}
	
	/**
	 * Public method used to determine the expiration duration of frozen 
	 * leftovers.
	 * @param frozen
	 * @return
	 */
	public static int ifFrozen(boolean frozen) {
		if(frozen) {
			return duration;
		}
		else return 3;
	}
	
	/**
	 * Method used to change the duration of how long a leftover can stay
	 * in the freezer without warning the user.
	 * @param duration
	 */
	public static void setFrozenDuration(int duration) {
		Leftover.duration = duration;
	}
	
	public boolean isLeftover() {
		return true;
	}

}
