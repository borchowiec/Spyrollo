package mainView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static mainView.Layout.ALCOHOL;
import static mainView.Layout.OTHER;

public class JsonHandler {
    private JsonObject json;

    public JsonHandler() {
        json = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        json.add("basics", jsonArray);
    }

    public void addAlcohol(String name, int percent, String color) {
        JsonArray jsonArray = json.getAsJsonArray("basics");
        JsonObject jsonAlcohol = getBasicElement(ALCOHOL, name, color);
        jsonAlcohol.addProperty("percent", percent);
        jsonArray.add(jsonAlcohol);
        save();
    }

    public void addOther(String name, String color) {
        JsonArray jsonArray = json.getAsJsonArray("basics");
        JsonObject jsonOther = getBasicElement(OTHER, name, color);
        jsonArray.add(jsonOther);
        save();
    }

    private JsonObject getBasicElement(int type, String name, String color) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", type);
        jsonObject.addProperty("name", name);
        jsonObject.addProperty("color", color);
        return jsonObject;
    }

    void save() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("liquids.json"));
            writer.write(json.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
