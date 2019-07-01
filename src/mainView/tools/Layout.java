package mainView.tools;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import mainView.Liquid;

import java.util.Random;

import static mainView.References.colors;

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

    private static VBox getArrowsPanel() {
        VBox panel = new VBox();
        panel.setAlignment(Pos.CENTER);

        Button up = new Button("∧");
        up.getStyleClass().addAll("arrowBtn", "up");

        Button down = new Button("∨");
        down.getStyleClass().addAll("arrowBtn", "down");

        panel.getChildren().addAll(up, down);
        return panel;
    }

    private static TextField getNameInput(Liquid liquid) {
        TextField name = new TextField(liquid.getName());
        name.getStyleClass().add("nameInput");
        name.textProperty().addListener((observable, oldValue, newValue) -> liquid.setName(newValue));
        return name;
    }

    private static void setUpSpinner(Spinner spinner) {
        spinner.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*"))
                spinner.getEditor().setText(newValue.replaceAll("[^\\d]", ""));
            if (newValue.length() == 0)
                spinner.getEditor().setText("1");
        });
        spinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                spinner.increment(0);
            }
        });
    }

    private static Spinner<Integer> getAmountSpinner(Liquid liquid) {
        Spinner<Integer> amount = new Spinner<>();
        amount.getStyleClass().add("amountSpinner");
        amount.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(
                        1,
                        Short.MAX_VALUE,
                        liquid.getAmount(),
                        100
                )
        );
        amount.setEditable(true);
        amount.valueProperty().addListener((observable, oldValue, newValue) -> liquid.setAmount(newValue));
        setUpSpinner(amount);
        return amount;
    }

    private static Spinner<Integer> getPercentageSpinner(Liquid liquid) {
        Spinner<Integer> percentage = new Spinner<>();
        percentage.getStyleClass().add("percentsSpinner");
        percentage.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, liquid.getPercent()));
        percentage.setEditable(true);
        percentage.valueProperty().addListener((observable, oldValue, newValue) -> liquid.setPercent(newValue));
        setUpSpinner(percentage);
        return percentage;
    }

    private static ColorPicker getColorPicker(Liquid liquid) {
        ColorPicker color = new ColorPicker();
        color.getStyleClass().add("colorPicker");
        color.getCustomColors().clear();

        for (String c : colors)
            color.getCustomColors().add(toColor(c));

        color.valueProperty().addListener((observable, oldValue, newValue) -> {
            liquid.setColor(toRGB(color.getValue()));
            color.setStyle("-fx-background-color: " + liquid.getColor());
        });
        color.setValue(toColor(liquid.getColor()));
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
        panel.getChildren().addAll(
                getArrowsPanel(),
                getNameInput(liquid),
                getAmountSpinner(liquid),
                new Label("ml"),
                getPercentageSpinner(liquid),
                new Label("%"),
                getColorPicker(liquid),
                getSaveButton(),
                getDeleteButton()
        );
        return panel;
    }

    public static HBox getOtherPanel(Liquid liquid) {
        HBox panel = getPanel();
        panel.getChildren().addAll(
                getArrowsPanel(),
                getNameInput(liquid),
                getAmountSpinner(liquid),
                new Label("ml"),
                getColorPicker(liquid),
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

        panel.getChildren().addAll(
                getArrowsPanel(),
                info,
                getDeleteButton()
        );
        return panel;
    }

    private static HBox getListElement(String name) {
        HBox element = new HBox();

        Label nameLabel = new Label(name);
        nameLabel.setStyle("-fx-pref-width: 1000px");
        nameLabel.setCursor(Cursor.HAND);

        element.getChildren().addAll(nameLabel, getDeleteButton());
        element.setAlignment(Pos.CENTER);
        return element;
    }

    public static HBox getAlcoholListElement(String name, int percents) {
        return getListElement(name + " " + percents+"%");
    }

    public static HBox getOtherListElement(String name) {
        return getListElement(name);
    }

    public static String getRandomColor() {
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

    public static Color toColor(String color) {
        return new Color(
                Integer.valueOf( color.substring( 1, 3 ), 16) / 256.0,
                Integer.valueOf( color.substring( 3, 5 ), 16) / 256.0,
                Integer.valueOf( color.substring( 5, 7 ), 16) / 256.0,
                1);
    }
}
