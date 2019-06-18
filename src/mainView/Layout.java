package mainView;

import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.util.Random;

public class Layout {
    public static final int ALCOHOL = 0;
    public static final int OTHER = 1;
    public static final int INFO = 2;

    public static HBox getAlcoholPanel(Liquid liquid) {
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

        amount.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Short.MAX_VALUE, 100, 100));
        percentage.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        amount.setEditable(true);
        percentage.setEditable(true);

        String bgColor = getRandomColor();
        color.setStyle("-fx-background-color: " + bgColor);

        liquid.setName(name.getText());
        liquid.setAmount(amount.getValue());
        liquid.setPercent(percentage.getValue());
        liquid.setColor(bgColor);
        amount.valueProperty().addListener((observable, oldValue, newValue) -> liquid.setAmount(newValue));
        percentage.valueProperty().addListener((observable, oldValue, newValue) -> liquid.setPercent(newValue));
        name.textProperty().addListener((observable, oldValue, newValue) -> liquid.setName(newValue));

        panel.getChildren().addAll(name, amount, new Label("ml"), percentage, new Label("%"), color, save, delete);
        return panel;
    }

    public static HBox getOtherPanel(Liquid liquid) {
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

        amount.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Short.MAX_VALUE, 100, 100));
        amount.setEditable(true);

        String bgColor = getRandomColor();
        color.setStyle("-fx-background-color: " + bgColor);

        liquid.setName(name.getText());
        liquid.setAmount(amount.getValue());
        liquid.setPercent(0);
        liquid.setColor(bgColor);
        amount.valueProperty().addListener((observable, oldValue, newValue) -> liquid.setAmount(newValue));
        name.textProperty().addListener((observable, oldValue, newValue) -> liquid.setName(newValue));

        panel.getChildren().addAll(name, amount, new Label("ml"), color, save, delete);
        return panel;
    }

    public static HBox getInfoPanel() {
        HBox panel = new HBox();

        TextField info = new TextField("Informacja");
        Button delete = new Button("Usuń");

        panel.getStyleClass().add("panel");
        info.getStyleClass().add("infoInput");
        delete.getStyleClass().add("deleteBtn");

        panel.getChildren().addAll(info, delete);
        return panel;
    }

    public static HBox getAlcoholListElement(String name, int percents) {
        HBox element = new HBox();

        Label nameLabel = new Label(name + " " + percents+"%");
        Button delete = new Button("Usuń");

        nameLabel.setStyle("-fx-pref-width: 1000px");
        delete.getStyleClass().add("deleteBtn");

        element.getChildren().addAll(nameLabel, delete);
        element.setAlignment(Pos.CENTER);
        return element;
    }

    public static HBox getOtherListElement(String name) {
        HBox element = new HBox();

        Label nameLabel = new Label(name);
        Button delete = new Button("Usuń");

        nameLabel.setStyle("-fx-pref-width: 1000px");
        delete.getStyleClass().add("deleteBtn");

        element.getChildren().addAll(nameLabel, delete);
        element.setAlignment(Pos.CENTER);
        return element;
    }

    private static String getRandomColor() {
        int i = new Random().nextInt(References.colors.length);
        return References.colors[i];
    }

    public static String toRGB(Color color) {
        String temp;

        StringBuilder sb = new StringBuilder("#");
        temp = Integer.toHexString((int) (color.getRed()*255));
        if (temp.length() <= 1)
            sb.append("0");
        sb.append(temp);

        temp = Integer.toHexString((int) (color.getGreen()*255));
        if (temp.length() <= 1)
            sb.append("0");
        sb.append(temp);

        temp = Integer.toHexString((int) (color.getBlue()*255));
        if (temp.length() <= 1)
            sb.append("0");
        sb.append(temp);

        return sb.toString();
    }
}
