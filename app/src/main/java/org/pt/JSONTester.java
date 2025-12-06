package org.pt;

import java.io.*;
import com.google.gson.*;
import com.google.gson.stream.*;

public class JSONTester {
    public static void main(String[] args) {
        String json = "{\"skipObj\": {\"skipKey\": \"skipValue\"}, \"obj\": {\"key\": \"value\"}}";
        try (JsonReader jsonReader = new JsonReader(new StringReader(json))) {
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                String fieldName = jsonReader.nextName();
                if (fieldName.equals("skipObj")) {
                    jsonReader.skipValue();
                } 
                else {
                    JsonElement jsonElement = JsonParser.parseReader(jsonReader);
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                }
            }
            jsonReader.endObject();
            System.out.println(json);
        }
        catch (IOException e) {
            System.out.println(67);
        }
    }
}
