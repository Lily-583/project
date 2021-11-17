//package models;
package lqiang_CSCI201_Assignment3;
//import util.YelpAPIParser;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class DriverInformation implements Serializable {
	private static final long serialVersionUID = 383927069613799417L;
    private final Location HQLocation;
    private final LinkedList<Order> orders;

    public DriverInformation(LinkedList<Order> orderlist,Location HQLocation) {
        this.orders = orderlist;
        this.HQLocation=HQLocation;
//        this.HQLocation = info.getLocation();
//        for (int i = 0; i != info.getItems().size(); i++) {
//            String restaurant = info.getRestaurants().get(i);
//            String item = info.getItems().get(i);
//            System.out.println("Restaurant: "+restaurant);
//            System.out.println("HeadQuarter: "+HQLocation.getLatitude()+" "+HQLocation.getLongitude());
//            System.out.println("before yelp "+TimeFormatter.getTimestamp());
//            Location location = YelpAPIParser.getLocation(restaurant, HQLocation);
//            System.out.println("after yelp "+TimeFormatter.getTimestamp());
//            orders.add(new Order(restaurant, item, location, 0));
        
        reorder(HQLocation);
    }

    public LinkedList<Order> getOrders() {
        return orders;
    }

    public Order getNext() {
        if (orders.size() == 0) {
            return null;
        } else {
            return orders.pollFirst();
        }
    }

    public void reorder(Location newLocation) {
        for (Order order : orders) {
            order.recalcDistance(newLocation);
        }
        orders.sort((o1, o2) -> {
            if (o1.getDistance() > o2.getDistance())
                return 1;
            else if (o1.getDistance() == o2.getDistance())
                return o1.getRestaurantName().compareTo(o2.getRestaurantName());
            else
                return -1;
        });
    }

    public Location getHQLocation() {
        return HQLocation;
    }

}
