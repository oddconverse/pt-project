package org.pt;
// NOTE!!!! NEVER COMMIT/PUSH GTFS DATABASE TO MAIN
// STOP FUCKING UP YOUR GIT
import java.io.*;
import java.net.URI;
import java.net.http.*;
import com.google.gson.*;
import com.google.gson.stream.*;

public class JSONTester {
    public static void main(String[] args) {
        String json = "{\"skipObj\": {\"skipKey\": \"skipValue\", \"diddy\": 67}, \"obj\": {\"key\": \"value\"}}";
        /* try (JsonReader jsonReader = new JsonReader(new StringReader(json))) {
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                String fieldName = jsonReader.nextName();

                JsonElement jsonElement = JsonParser.parseReader(jsonReader);
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                System.out.println(jsonObject.toString());
            }
            jsonReader.endObject();
            System.out.println(json);
        }
        catch (IOException e) {
            System.out.println(67);
        } */
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:2322/reverse?lon=145.120758&lat=-37.924732"))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            String district = null;
            String postcode = null;
            String city = null;
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
                    district = reader.nextString();
                }
                else if (fieldName.equals("postcode")) {
                    postcode = reader.nextString();
                }
                else if (fieldName.equals("city")) {
                    city = reader.nextString();
                }
                else {
                    reader.skipValue();
                }
            }
            System.out.println(String.format("%s, %s, %s",  district, postcode, city));
            reader.endObject();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
