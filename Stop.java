public class Stop implements Comparable<Stop>{
    private String name;
    private String id;
    private float lat;
    private float lon;
    private String suburb;
    public Stop(String name, String id, float lat, float lon) {
        this.name = name;
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.suburb = findSuburb();
    }
    public String findSuburb() {
        String apiCommand = String.format("http://localhost:2322/reverse?lon=%f&lat=%f", getLongitude(), getLatitude());
        // run api command and get suburb
        return null;
    }
    public String saveString() {
        return String.format("\"%s\",\"%s\",\"%f\",\"%f\"", name, id, lat, lon);
    }
    public float getLongitude() {
        return lon;
    }
    public float getLatitude() {
        return lat;
    }
    public String getID() {
        return id;
    }
    @Override
    public int compareTo(Stop stop) {
        return this.getID().compareTo(stop.getID());
    }
}
