package mainView.tools;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import mainView.Controller;
import mainView.Liquid;

import java.io.*;

import static mainView.tools.Layout.*;

/**
 * This class has methods that solves problems with json e.g. saving or loading data.
 * @author Patryk Borchowiec
 */
public class JsonHandler {
    private JsonObject liquidsJson;

    public JsonHandler() {
        File jsonFile = new File("liquids.json");
        if (!jsonFile.exists())
            createBasicLiquidsJson();
    }

    /**
     * This method creates basic json that contains list of basic liquids.
     */
    private void createBasicLiquidsJson() {
        liquidsJson = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        liquidsJson.add("liquids", jsonArray);
        addAlcoholToLiquids("Wódka", 40, "#bad4ff");
        addAlcoholToLiquids("Piwo", 5, "#ffe122");
    }

    /**
     * This method adds to liquids list and save into file, new alcohol.
     * @param name Name of alcohol
     * @param percent Percentage of alcohol
     * @param color Color of alcohol
     */
    public void addAlcoholToLiquids(String name, int percent, String color) {
        JsonArray jsonArray = liquidsJson.getAsJsonArray("liquids");
        JsonObject jsonAlcohol = getBasicElement(ALCOHOL, name, color);
        jsonAlcohol.addProperty("percent", percent);
        jsonArray.add(jsonAlcohol);
        saveLiquids();
    }

    /**
     * This method adds to liquids list and save into file, new liquid.
     * @param name Name of liquid
     * @param color Color of liquid
     */
    public void addOtherToLiquids(String name, String color) {
        JsonArray jsonArray = liquidsJson.getAsJsonArray("liquids");
        JsonObject jsonOther = getBasicElement(OTHER, name, color);
        jsonArray.add(jsonOther);
        saveLiquids();
    }

    /**
     * This method creates basic element of liquids list.
     * @param type Type of liquid. Alcohol or other
     * @param name Name of liquid
     * @param color Color of liquid
     * @return Basic json element
     */
    private JsonObject getBasicElement(int type, String name, String color) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", type);
        jsonObject.addProperty("name", name);
        jsonObject.addProperty("color", color);
        return jsonObject;
    }

    /**
     * This method removes element from liquids list
     * @param i Index of the removed element
     */
    public void removeFromLiquidsList(int i) {
        liquidsJson.get("liquids").getAsJsonArray().remove(i);
        saveLiquids();
    }

    /**
     * Converts file into json object.
     * @param file File that you want to convert
     * @return Json object
     */
    public static JsonObject toJsonObject(File file) throws Exception {
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(new FileReader(file));
        return jsonElement.getAsJsonObject();
    }

    /**
     * This methods loads liquids from liquids.json and puts it into liquids list.
     * @param controller Controller that has liquid list.
     */
    public void loadLiquids(Controller controller) {
        try {
            liquidsJson = toJsonObject(new File("liquids.json"));
        } catch (Exception e) {
            createBasicLiquidsJson();
        }

        JsonArray array = liquidsJson.getAsJsonArray("liquids");
        if (array == null) {
            createBasicLiquidsJson();
            array = liquidsJson.getAsJsonArray("liquids");
        }

        for (JsonElement el : array) {
            JsonObject obj = el.getAsJsonObject();
            try {
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
            } catch (NullPointerException e) {
                array.remove(el);
                saveLiquids();
                loadLiquids(controller);
                break;
            }
        }
    }

    /**
     * This method save liquids list into liquids.json file
     */
    private void saveLiquids() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("liquids.json"));
            writer.write(liquidsJson.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method saves recipe into recipes directory. Recipes has <code>.spyr</code> extension.
     * @param title Title of recipe
     * @param elements List of elements, such as alcohol, other liquids or information.
     * @throws IOException
     */
    public void saveRecipe(String title, ObservableList<Node> elements) throws IOException {
        JsonObject json = new JsonObject();
        JsonArray array = new JsonArray();

        json.addProperty("title", title);

        for (Node el : elements) {
            JsonObject temp = new JsonObject();
            if (el.lookup(".amountSpinner") != null) {
                String color = Layout.toRGB(((ColorPicker)el.lookup(".colorPicker")).getValue());
                temp.addProperty("name", ((TextField) el.lookup(".nameInput")).getText());
                temp.addProperty("amount", (int) ((Spinner) el.lookup(".amountSpinner")).getValue());
                temp.addProperty("color", color);
                if (el.lookup(".percentsSpinner") != null) {
                    temp.addProperty("percents", (int) ((Spinner) el.lookup(".percentsSpinner")).getValue());
                    temp.addProperty("type", ALCOHOL);
                }
                else
                    temp.addProperty("type", OTHER);
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