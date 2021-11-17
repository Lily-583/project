//package PA3.models;
package lqiang_CSCI201_Assignment3;

import java.io.Serializable;

public class Location implements Serializable {
//	public class Location{
    private static final long serialVersionUID = 6174418428708492256L;

////    public final double latitude;
//    public final double longitude;
    public double latitude;
    public double longitude;

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

	public double getLongitude() {
        return longitude;
    }

}
