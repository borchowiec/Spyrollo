package mainView;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.util.Random;

import static mainView.References.*;

public class Layout {
    public static final int ALCOHOL = 0;
    public static final int OTHER = 1;
    public static final int INFO = 2;

    private static HBox getPanel() {
        HBox panel = new HBox();
        panel.setSpacing(15);
        panel.setAlignment(Pos.CENTER);
        panel.getStyleClass().add("panel");
        return panel;
    }

    private static TextField getNameInput(Liquid liquid) {
        TextField name = new TextField(liquid.getName());
        name.getStyleClass().add("nameInput");
        name.textProperty().addListener((observable, oldValue, newValue) -> liquid.setName(newValue));
        return name;
    }

    private static Spinner<Integer> getAmountSpinner(Liquid liquid) {
        Spinner<Integer> amount = new Spinner<>();
        amount.getStyleClass().add("amountSpinner");
        amount.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Short.MAX_VALUE, liquid.getAmount(), 100));
        amount.setEditable(true);
        amount.valueProperty().addListener((observable, oldValue, newValue) -> liquid.setAmount(newValue));
        return amount;
    }

    private static Button getColorButton(Liquid liquid) {
        Button color = new Button();
        color.getStyleClass().add("colorBtn");
        color.setStyle("-fx-background-color: " + liquid.getColor());
        return color;
    }

    private static Button getSaveButton() {
        Button save = new Button("Zapisz");
        save.getStyleClass().add("saveBtn");

        return save;
    }

    private static Button getDeleteButton() {
        Button delete = new Button("Usuń");
        delete.getStyleClass().add("deleteBtn");
        return delete;
    }

    public static HBox getAlcoholPanel(Liquid liquid) {
        HBox panel = getPanel();

        Spinner<Integer> percentage = new Spinner<>();
        percentage.getStyleClass().add("percentsSpinner");
        percentage.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, liquid.getPercent()));
        percentage.setEditable(true);
        percentage.valueProperty().addListener((observable, oldValue, newValue) -> liquid.setPercent(newValue));

        panel.getChildren().addAll(
                getNameInput(liquid),
                getAmountSpinner(liquid),
                new Label("ml"),
                percentage,
                new Label("%"),
                getColorButton(liquid),
                getSaveButton(),
                getDeleteButton()
        );

        return panel;
    }

    public static HBox getOtherPanel(Liquid liquid) {
        HBox panel = getPanel();

        panel.getChildren().addAll(
                getNameInput(liquid),
                getAmountSpinner(liquid),
                new Label("ml"),
                getColorButton(liquid),
                getSaveButton(),
                getDeleteButton()
        );

        return panel;
    }

    public static HBox getInfoPanel() {
        HBox panel = new HBox();
        panel.getStyleClass().add("panel");

        TextField info = new TextField("Informacja");
        info.getStyleClass().add("infoInput");

        panel.getChildren().addAll(info, getDeleteButton());
        return panel;
    }

    public static HBox getAlcoholListElement(String name, int percents) {
        HBox element = new HBox();

        Label nameLabel = new Label(name + " " + percents+"%");
        nameLabel.setStyle("-fx-pref-width: 1000px");
        nameLabel.setCursor(Cursor.HAND);

        element.getChildren().addAll(nameLabel, getDeleteButton());
        element.setAlignment(Pos.CENTER);
        return element;
    }

    public static HBox getOtherListElement(String name) {
        HBox element = new HBox();

        Label nameLabel = new Label(name);
        nameLabel.setStyle("-fx-pref-width: 1000px");
        nameLabel.setCursor(Cursor.HAND);

        element.getChildren().addAll(nameLabel, getDeleteButton());
        element.setAlignment(Pos.CENTER);
        return element;
    }

    static String getRandomColor() {
        int i = new Random().nextInt(colors.length);
        return colors[i];
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
