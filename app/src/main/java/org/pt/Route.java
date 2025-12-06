package org.pt;

import java.util.*;

public class Route implements Comparable<Route> {
    private String id;
    private String shortName;
    private String longName;
    private BinarySearchTree<String, Stop> stops;
    private ArrayList<String> tripIDs;
    public Route(String id, String numberName, String longName) {
        this.id = id;
        this.shortName = numberName;
        this.longName = longName;
        this.stops = new BinarySearchTree<>();
        this.tripIDs = new ArrayList<>();
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
    public boolean hasTrip(String tripID) {
        for (String id : tripIDs) {
            if (id.equals(tripID)){
                return true;
            }
        }
        return false;
    }
    public void addTrip(String tripID) {
        tripIDs.add(tripID);
    }
    public String getAllTrips() {
        String str = "";
        for (String id : tripIDs) {
            str += id;
        }
        return str;
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
