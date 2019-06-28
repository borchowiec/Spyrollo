package mainView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

import java.io.*;

import static mainView.Layout.*;

public class JsonHandler {
    private JsonObject liquidsJson;

    public JsonHandler() {
        File jsonFile = new File("liquids.json");
        if (!jsonFile.exists()) {
            liquidsJson = new JsonObject();
            JsonArray jsonArray = new JsonArray();
            liquidsJson.add("liquids", jsonArray);
            addAlcoholToLiquids("Wódka", 40, "#bad4ff");
            addAlcoholToLiquids("Piwo", 5, "#ffe122");
        }
    }

    public void addAlcoholToLiquids(String name, int percent, String color) {
        JsonArray jsonArray = liquidsJson.getAsJsonArray("liquids");
        JsonObject jsonAlcohol = getBasicElement(ALCOHOL, name, color);
        jsonAlcohol.addProperty("percent", percent);
        jsonArray.add(jsonAlcohol);
        saveLiquids();
    }

    public void addOtherToLiquids(String name, String color) {
        JsonArray jsonArray = liquidsJson.getAsJsonArray("liquids");
        JsonObject jsonOther = getBasicElement(OTHER, name, color);
        jsonArray.add(jsonOther);
        saveLiquids();
    }

    public void removeFromLiquidsList(int i) {
        liquidsJson.get("liquids").getAsJsonArray().remove(i);
        saveLiquids();
    }

    private JsonObject getBasicElement(int type, String name, String color) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", type);
        jsonObject.addProperty("name", name);
        jsonObject.addProperty("color", color);
        return jsonObject;
    }

    public static JsonObject toJsonObject(File file) {
        try {
            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(new FileReader(file));
            return jsonElement.getAsJsonObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new JsonObject();
    }

    public void loadLiquids(Controller controller) {
        liquidsJson = toJsonObject(new File("liquids.json"));

        JsonArray array = liquidsJson.getAsJsonArray("liquids");

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

    private void saveLiquids() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("liquids.json"));
            writer.write(liquidsJson.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveRecipe(String title, ObservableList<Node> elements) throws IOException {
        JsonObject json = new JsonObject();
        JsonArray array = new JsonArray();

        json.addProperty("title", title);

        for (Node el : elements) {
            JsonObject temp = new JsonObject();
            if (el.lookup(".percentsSpinner") != null) {
                String color = Layout.toRGB(((ColorPicker)el.lookup(".colorPicker")).getValue());
                temp.addProperty("type", ALCOHOL);
                temp.addProperty("name", ((TextField) el.lookup(".nameInput")).getText());
                temp.addProperty("percents", (int) ((Spinner) el.lookup(".percentsSpinner")).getValue());
                temp.addProperty("amount", (int) ((Spinner) el.lookup(".amountSpinner")).getValue());
                temp.addProperty("color", color);
            }
            else if (el.lookup(".amountSpinner") != null) {
                String color = Layout.toRGB(((ColorPicker)el.lookup(".colorPicker")).getValue());
                temp.addProperty("type", OTHER);
                temp.addProperty("name", ((TextField) el.lookup(".nameInput")).getText());
                temp.addProperty("amount", (int) ((Spinner) el.lookup(".amountSpinner")).getValue());
                temp.addProperty("color", color);
            }
            else {
                temp.addProperty("type", INFO);
                temp.addProperty("content", ((TextField) el.lookup(".infoInput")).getText());
            }

            array.add(temp);
        }

        json.add("elements", array);
        new File("recipes").mkdir();

        BufferedWriter writer = new BufferedWriter(new FileWriter("recipes" + File.separator + title + ".spyr"));
        writer.write(json.toString());
        writer.close();
    }
}
