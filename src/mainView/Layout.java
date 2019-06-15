package mainView;

import com.sun.prism.paint.Color;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;

import java.util.Random;

public class Layout {
    public static HBox getAlcoholPanel() {
        HBox panel = new HBox();

        TextField name = new TextField("Alkohol");
        Spinner<Integer> amount = new Spinner<>();
        Spinner<Integer> percentage = new Spinner<>();
        Button color = new Button();
        Button save = new Button("Zapisz");
        Button delete = new Button("Usuń");

        panel.setSpacing(15);
        panel.setAlignment(Pos.CENTER);

        panel.getStyleClass().add("panel");
        name.getStyleClass().add("nameInput");
        amount.getStyleClass().add("amountSpinner");
        percentage.getStyleClass().add("percentageSpinner");
        color.getStyleClass().add("colorBtn");
        save.getStyleClass().add("saveBtn");
        delete.getStyleClass().add("deleteBtn");

        amount.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Short.MAX_VALUE, 0, 100));
        percentage.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        amount.setEditable(true);
        percentage.setEditable(true);

        color.setStyle("-fx-background-color: " + getRandomColor());

        panel.getChildren().addAll(name, amount, new Label("ml"), percentage, new Label("%"), color, save, delete);
        return panel;
    }

    public static HBox getOtherPanel() {
        HBox panel = new HBox();

        TextField name = new TextField("Napój");
        Spinner<Integer> amount = new Spinner<>();
        Button color = new Button();
        Button save = new Button("Zapisz");
        Button delete = new Button("Usuń");

        panel.setSpacing(15);
        panel.setAlignment(Pos.CENTER);

        panel.getStyleClass().add("panel");
        name.getStyleClass().add("nameInput");
        amount.getStyleClass().add("amountSpinner");
        color.getStyleClass().add("colorBtn");
        save.getStyleClass().add("saveBtn");
        delete.getStyleClass().add("deleteBtn");

        amount.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Short.MAX_VALUE, 0, 100));
        amount.setEditable(true);

        color.setStyle("-fx-background-color: " + getRandomColor());

        panel.getChildren().addAll(name, amount, new Label("ml"), color, save, delete);
        return panel;
    }

    public static HBox getInfoPanel() {
        HBox panel = new HBox();

        TextField info = new TextField("Informacja");

        panel.getStyleClass().add("panel");
        info.getStyleClass().add("infoInput");

        panel.getChildren().addAll(info);
        return panel;
    }

    private static String getRandomColor() {
        Random random = new Random();
        int nextInt = random.nextInt(0xffffff + 1);
        return String.format("#%06x", nextInt);
    }
}
