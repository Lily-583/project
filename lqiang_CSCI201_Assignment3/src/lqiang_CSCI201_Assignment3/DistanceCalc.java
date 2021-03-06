//package PA3.util;
package lqiang_CSCI201_Assignment3;

//import PA3.models.Location;

public class DistanceCalc {
    public static double getDistance(Location loc1, Location loc2) {
        double delta = loc1.getLongitude() - loc2.getLongitude();
        double dist = Math.sin(Math.toRadians(loc1.getLatitude())) * Math.sin(Math.toRadians(loc2.getLatitude()))
                + Math.cos(Math.toRadians(loc1.getLatitude())) * Math.cos(Math.toRadians(loc2.getLatitude()))
                * Math.cos(Math.toRadians(delta));
        dist = Math.acos(dist);
        dist = 3963.0 * dist;
        return dist;
    }
}
