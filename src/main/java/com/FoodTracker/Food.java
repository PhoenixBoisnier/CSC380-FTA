package main.java.com.FoodTracker;
/**
 * The main item in the program, stores all the info about the food items.
 * This class tracks food based on its name, cost, days to expiration, and 
 * the system time when the food was placed into the system. Food will keep 
 * track of how close it is to expiring as well as if it is expired.
 * 
 * @author phoenix
 *
 */
public class Food {
	
	public static boolean isLeftover = false;
	
	private String name;
	private double cost;
	private int daysToExpire;
	private long time;
	private long expiration;
	
	/**
	 * A constructor for a new food item.
	 * @param name
	 * @param cost
	 * @param daysToExpire
	 */
	public Food(String name, double cost, int daysToExpire) {
		this.name = name;
		this.cost = cost;
		this.daysToExpire = daysToExpire;
		time = System.currentTimeMillis();
		//Expiration is the time determined by adding the number of days to
		//expiration to the system time when the food was input
		expiration = time+(this.daysToExpire*FoodTrackerApp.millisecondsInDay);
	}
	
	/**
	 * Special constructor used by the file parser.
	 * @param name
	 * @param cost
	 * @param daysToExpire
	 * @param time
	 * @param exp
	 */
	public Food(String name, double cost, int daysToExpire, long time, 
			long exp) {
		this.name = name;
		this.cost = cost;
		this.daysToExpire = daysToExpire;
		this.time = time;
		this.expiration = exp;
	}
	
	/**
	 * Special method used by the parser.
	 * @return
	 */
	public String saveFood() {
		String retVal = "";
		retVal+=name+"\n";
		retVal+=cost+"\n";
		retVal+=daysToExpire+"\n";
		retVal+=time+"\n";
		retVal+=expiration+"\n";
		return retVal;
	}
	
	public String privateDeets() {
		String retVal = "";
		retVal+=daysToExpire+"\n";
		retVal+=time+"\n";
		retVal+=expiration+"\n";
		return retVal;
	}

	/**
	 * Gets cost.
	 * @return
	 */
	public double getCost() {
		return cost;
	}
	
	/**
	 * Gets name of food.
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * This method should take the waiting time and see if the time from 
	 * insert is "t" days away from the expiration time.
	 * @param t Preferred time after
	 * @return
	 */
	public boolean aboutToExpire(int t) {
		//This value then has the warning window time subtracted from it and 
		//checked against the current time. If the current time is greater than
		//or equal to this new value, the food is close to expiring.
		if((expiration-(t*FoodTrackerApp.millisecondsInDay)
				<= System.currentTimeMillis())) {
			return true;
		}
		else return false;
	}
	
	/**
	 * Checks the current system time to determine if the food is expired.
	 * @return
	 */
	public boolean isExpired() {
		if(expiration <= System.currentTimeMillis()) {
			return true;
		}
		else return false;
	}

}
