package mainView.windows;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.File;

/**
 * This class is responsible for creating and displaying window, where you can set up properties of saving pdf e.g.
 * if liquids should be in amounts or proportions, or path to pdf.
 * @author Patryk Borchowiec
 */
public class PdfOptionsWindow {
    public static int AMOUNTS = 0;
    public static int PROPORTIONS = 1;

    /**
     * This pair is a value that will be returns after displaying window.
     * Key is a type of displaying liquids. In amounts '0' or proportions '1'.
     * File is a path of pdf.
     */
    private static Pair<Integer, File> properties;

    /**
     * This method creates, displays window and waits for choosing options.
     * @return Chosen properties
     */
    public static Pair<Integer, File> display() {
        properties = new Pair<>(AMOUNTS, new File("Untitled.pdf"));
        Stage window = BasicWindow.getBasicWindow("Zapisz do PDF");
        window.setOnCloseRequest(event -> properties = null);

        ToggleGroup group = new ToggleGroup();
        RadioButton amountsRadio = new RadioButton("Ilości");
        amountsRadio.setOnAction(event -> properties = new Pair<>(AMOUNTS, properties.getValue()));
        amountsRadio.setToggleGroup(group);
        RadioButton proportionsRadio = new RadioButton("Proporcje");
        proportionsRadio.setToggleGroup(group);
        proportionsRadio.setOnAction(event -> properties = new Pair<>(PROPORTIONS, properties.getValue()));
        amountsRadio.setSelected(true);

        Button saveBtn = new Button("Zapisz");
        saveBtn.setOnAction(event -> window.close());
        saveBtn.getStyleClass().add("saveBtn");

        HBox fileBox = new HBox();
        fileBox.setAlignment(Pos.CENTER);
        TextField path = new TextField(properties.getValue().getPath());
        path.setEditable(false);
        path.setMinWidth(300);

        Button fileChooserBtn = new Button("Zapisz do...");
        fileChooserBtn.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();

            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF documents (*.pdf)", "*.pdf");
            fileChooser.getExtensionFilters().add(extFilter);

            File temp = fileChooser.showSaveDialog(window);
            if (temp != null) {
                properties = new Pair<>(properties.getKey(), temp);
                path.setText(temp.getPath());
            }
        });
        fileChooserBtn.getStyleClass().add("btn");
        fileChooserBtn.setStyle("-fx-background-color: #388e3c");
        fileBox.getChildren().addAll(path, fileChooserBtn);

        Region spacer = new Region();
        spacer.setMinHeight(15);

        VBox layout = new VBox(5);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(5));
        layout.getChildren().addAll(amountsRadio, proportionsRadio, spacer, fileBox, saveBtn);

        BasicWindow.setScene(window, layout);

        return properties;
    }
}
