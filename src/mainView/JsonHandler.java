package mainView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;

import static mainView.Layout.ALCOHOL;
import static mainView.Layout.OTHER;

public class JsonHandler {
    private JsonObject json;

    public JsonHandler() {
        File jsonFile = new File("liquids.json");
        if (!jsonFile.exists()) {
            json = new JsonObject();
            JsonArray jsonArray = new JsonArray();
            json.add("liquids", jsonArray);
            addAlcohol("Wódka", 40, "#bad4ff");
            addAlcohol("Piwo", 5, "#ffe122");
        }
    }

    public void addAlcohol(String name, int percent, String color) {
        JsonArray jsonArray = json.getAsJsonArray("liquids");
        JsonObject jsonAlcohol = getBasicElement(ALCOHOL, name, color);
        jsonAlcohol.addProperty("percent", percent);
        jsonArray.add(jsonAlcohol);
        save();
    }

    public void addOther(String name, String color) {
        JsonArray jsonArray = json.getAsJsonArray("liquids");
        JsonObject jsonOther = getBasicElement(OTHER, name, color);
        jsonArray.add(jsonOther);
        save();
    }

    public void removeFromLiquidList(int i) {
        json.get("liquids").getAsJsonArray().remove(i);
        save();
    }

    private JsonObject getBasicElement(int type, String name, String color) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", type);
        jsonObject.addProperty("name", name);
        jsonObject.addProperty("color", color);
        return jsonObject;
    }

    public void load(Controller controller) {
        try {
            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(new FileReader("liquids.json"));
            json = jsonElement.getAsJsonObject();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JsonArray array = json.getAsJsonArray("liquids");

        for (JsonElement el : array) {
            JsonObject obj = el.getAsJsonObject();
            switch (obj.get("type").getAsInt()) {
                case ALCOHOL:
                    controller.addAlcoholToLiquidList(new Liquid(
                            obj.get("name").getAsString(),
                            0,
                            obj.get("percent").getAsInt(),
                            obj.get("color").getAsString()));
                    break;
                case OTHER:
                    controller.addOtherToLiquidList(new Liquid(
                            obj.get("name").getAsString(),
                            0,
                            0,
                            obj.get("color").getAsString()));
                    break;
            }
        }
    }

    private void save() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("liquids.json"));
            writer.write(json.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
