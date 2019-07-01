package mainView.windows;

import com.google.gson.JsonObject;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import mainView.tools.JsonHandler;

import java.io.File;
import java.util.Objects;

public class RecipeChooser {
    private static JsonObject selectedItem;

    public static JsonObject display() {
        selectedItem = null;
        Stage window = BasicWindow.getBasicWindow("Wybierz przepis");
        BorderPane borderPane = new BorderPane();

        ListView<JsonObject> listView = new ListView<>();
        listView.setCellFactory(lv -> new ListCell<JsonObject>() {
            @Override
            public void updateItem(JsonObject item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    String text = item.get("title").getAsString();
                    setText(text);
                }
            }
        });

        File dir = new File("recipes");
        for (File f : Objects.requireNonNull(dir.listFiles()))
            if (f.getName().contains(".spyr") && f.getName().substring(f.getName().indexOf('.') + 1).equalsIgnoreCase("spyr"))
                listView.getItems().add(JsonHandler.toJsonObject(f));

        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedItem = newValue;
            window.close();
        });

        borderPane.setCenter(listView);
        BasicWindow.setScene(window, borderPane);

        return selectedItem;
    }
}
