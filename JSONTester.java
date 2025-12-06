package org.example;

import com.google.json;
import java.io.*;

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
        }
    }
}
