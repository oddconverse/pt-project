import java.net.http.*;
import java.io.*;
import java.net.URI;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.*;

public class Stop implements Comparable<Stop>{
    private String name;
    private String id;
    private double lat;
    private double lon;
    private String suburb;
    public Stop(String name, String id, double lat, double lon) {
        this.name = name;
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.suburb = null;
    }
    public Stop(String name, String id, double lat, double lon, String suburb) {
        this.name = name;
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.suburb = suburb;
    }
    public String findSuburb() {
        String apiCommand = String.format("http://localhost:2322/reverse?lon=%f&lat=%f", getLongitude(), getLatitude());
        try {
            HttpClient httpClient = HttpClient.newHttpClient();  
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(String.format("http://localhost:2322/reverse?lon=%f&lat=%f", getLongitude(), getLatitude())))
                                                            .GET()
                                                            .build();
            HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
            System.out.println(response.body());
            // run api command and get suburb
            FileWriter outfile = new FileWriter("json.txt");
            outfile.append(response.body());
            int suburbStart = response.body().indexOf("district") + 11;
            System.out.println(suburbStart);
            int suburbEnd = response.body().indexOf("\"", suburbStart);
            System.out.println(suburbEnd);
            suburb = response.body().substring(suburbStart, suburbEnd);
            outfile.close();
            return suburb;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public String saveString() {
        return String.format("\"%s\",\"%s\",\"%f\",\"%f\", \"%s\"", name, id, lat, lon, suburb);
    }
    public double getLongitude() {
        return lon;
    }
    public double getLatitude() {
        return lat;
    }
    public String getID() {
        return id;
    }
    public String getSuburb() {
        return suburb;
    }
    @Override
    public int compareTo(Stop stop) {
        return this.getID().compareTo(stop.getID());
    }
    public static void main(String[] args) {
        Stop stop = new Stop("Gay sex lane", "111111", -37.9242397, 145.1201882);
        System.out.println(stop.findSuburb());
    }
}
