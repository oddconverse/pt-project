package org.pt;
// NOTE!!!! NEVER COMMIT/PUSH GTFS DATABASE TO MAIN
// STOP FUCKING UP YOUR GIT
import com.google.gson.stream.JsonReader;

import java.net.http.*;
import java.io.*;
import java.net.URI;

public class Stop implements Comparable<Stop>{
    private String name;
    private String id;
    private double lat;
    private double lon;
    private String suburb;
    private String postcode;
    public Stop(String name, String id, double lat, double lon) {
        this.name = name;
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.suburb = null;
        this.postcode = null;
    }
    public Stop(String name, String id, double lat, double lon, String suburb) {
        this.name = name;
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        if (suburb != null) {
            this.suburb = suburb;
        }
        else {
            this.suburb = null;
        }
        this.postcode = null;
    }
    public Stop(String name, String id, double lat, double lon, String suburb, String postcode) {
        this.name = name;
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        if (suburb != null)
            this.suburb = suburb;
        else
            this.suburb = null;
        if (postcode != null)
            this.postcode = postcode;
        else
            this.postcode = null;
    }
    public String findSuburb() {
        /*String apiCommand = String.format("http://localhost:2322/reverse?lon=%f&lat=%f", getLongitude(), getLatitude());
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
        }*/
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(String.format("http://localhost:2322/reverse?lon=%f&lat=%f", lon, lat)))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String districtTemp = null;
            String postcodeTemp = null;
            String cityTemp = null;
            System.out.println(response.body());
            JsonReader reader = new JsonReader(new StringReader(response.body()));
            reader.beginObject();
            while (reader.hasNext()) {
                String fieldName = reader.nextName();
                if (fieldName.equals("features")) {
                    reader.beginArray();
                    reader.beginObject();
                }
                else if (fieldName.equals("properties")) {
                    reader.beginObject();
                }
                else if (fieldName.equals("district")) {
                    districtTemp = reader.nextString();
                }
                else if (fieldName.equals("postcode")) {
                    postcodeTemp = reader.nextString();
                }
                else if (fieldName.equals("city")) {
                    cityTemp = reader.nextString();
                }
                else {
                    reader.skipValue();
                }
            }
            System.out.println(String.format("%s, %s, %s",  districtTemp, postcodeTemp, cityTemp));
            reader.endObject();
            if (districtTemp != null) {
                suburb = districtTemp;
            }
            else {
                suburb = cityTemp;
            }
            if (postcodeTemp != null) {
                postcode = postcodeTemp;
            }
        }
        catch (Exception e)
        {
            findSuburb();
        }
        return saveString();
    }
    public String saveString() {
        return String.format("\"%s\",\"%s\",\"%f\",\"%f\", \"%s\", \"%s\"", name, id, lat, lon, suburb, postcode);
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
    public String getPostcode() {return postcode;}
    @Override
    public int compareTo(Stop stop) {
        return this.getID().compareTo(stop.getID());
    }
    public static void main(String[] args) {
        Stop stop = new Stop("Gay sex lane", "111111", -37.933968, 145.084473);
        System.out.println(stop.findSuburb());
    }
}
