package mainView.windows;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.File;

public class PdfOptionsFrame {
    public static int AMOUNTS = 0;
    public static int PROPORTIONS = 1;

    private static Pair<Integer, File> properties;

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
        fileBox.getChildren().addAll(path, fileChooserBtn);

        VBox layout = new VBox(5);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(5));
        layout.getChildren().addAll(amountsRadio, proportionsRadio, fileBox, saveBtn);

        BasicWindow.setScene(window, layout);

        return properties;
    }
}
