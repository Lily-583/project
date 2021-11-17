//package PA3.models;
package lqiang_CSCI201_Assignment3;
import java.io.Serializable;

//import PA3.util.DistanceCalc;

public class Order implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2711307422885008735L;
	private final String restaurantName;
    private final String itemName;
    private final Location location;
    private double distance;
    private final int starttime;

    public Order( int starttime,String restaurantName, String itemName, Location location, double distance) {
        this.restaurantName = restaurantName;
        this.itemName = itemName;
        this.location = location;
        this.distance = distance;
        this.starttime=starttime;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getItemName() {
        return itemName;
    }

    public Location getLocation() {
        return location;
    }

    public double getDistance() {
        return distance;
    }
    
    public double getStartTime() {
    	return starttime;
    	
    }

    public void recalcDistance(Location newLocation) {
        distance = DistanceCalc.getDistance(location, newLocation);
    }

}
