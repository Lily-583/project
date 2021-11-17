//package PA3.models;
package lqiang_CSCI201_Assignment3;

public class Event {

    private final int time;
    private final String startLocation;
    private final String itemName;

    public Event(int time, String startLocation, String itemName) {
        this.time = time;
        this.startLocation = startLocation;
        this.itemName = itemName;
    }

    public int getTime() {
        return time;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public String getItemName() {
        return itemName;
    }

}
