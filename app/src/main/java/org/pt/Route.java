package org.pt;
// NOTE!!!! NEVER COMMIT/PUSH GTFS DATABASE TO MAIN
// STOP FUCKING UP YOUR GIT
import java.util.*;

public class Route implements Comparable<Route> {
    private String id;
    private String shortName;
    private String longName;
    private BinarySearchTree<String, Stop> stops;
    public Route(String id, String numberName, String longName) {
        this.id = id;
        this.shortName = numberName;
        this.longName = longName;
        this.stops = new BinarySearchTree<>();
    }
    public Route(String numberName) {
        this.shortName = numberName;
        this.longName = null;
    }
    public void populateStops() {
        
    }
    public int getStopSize() {
        return stops.size();
    }
    public String getID() {
        return id;
    }
    public String getShortName() {
        return shortName;
    }
    public String getLongName() {
        return longName;
    }
    public String toString() {
        return shortName + " " + longName;
    }
    public String saveString() {
        return String.format("\"%s\",\"%s\",\"%s\"", getID(), getShortName(), getLongName());
    }
    @Override
    public int compareTo(Route o) {
        return this.getID().compareTo(o.getID());
    }
}
