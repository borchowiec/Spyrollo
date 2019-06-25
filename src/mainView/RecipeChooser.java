package mainView;

import com.google.gson.JsonObject;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

import java.io.File;

public class RecipeChooser {
    public static JsonObject display() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Wybierz przepis");
        window.setMinWidth(450);

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
        for (File f : dir.listFiles())
            if (f.getName().contains(".spyr") && f.getName().substring(f.getName().indexOf('.') + 1).equalsIgnoreCase("spyr"))
                listView.getItems().add(JsonHandler.toJsonObject(f));


        final JsonObject[] selectedItem = new JsonObject[1];
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedItem[0] = newValue;
            window.close();
        });

        borderPane.setCenter(listView);
        Scene scene = new Scene(borderPane);
        window.setScene(scene);
        window.showAndWait();

        return selectedItem[0];
    }
}
