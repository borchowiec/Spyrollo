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

/**
 * This class contains methods that creates and set ups layout elements.
 * @author Patryk Borchowiec
 */
public class Layout {
    public static final int ALCOHOL = 0;
    public static final int OTHER = 1;
    public static final int INFO = 2;

    /**
     * This method creates basic panel which is a container for other nodes. Used in main container to represents
     * alcohol, other liquids and information.
     * @return Basic panel
     */
    private static HBox getPanel() {
        HBox panel = new HBox();
        panel.setSpacing(15);
        panel.setAlignment(Pos.CENTER);
        panel.getStyleClass().add("panel");
        return panel;
    }

    /**
     * This method creates <code>VBox</code> that contains arrow buttons that moves whole panel up and down. Used in main container.
     * @return VBox with arrows
     */
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

    /**
     * This method creates text field that should contain name of alcohol or other liquids. Used in main container.
     * @param liquid The fluid whose name will be updated by this text field.
     * @return Text field that contains name of liquid.
     */
    private static TextField getNameInput(Liquid liquid) {
        TextField name = new TextField(liquid.getName());
        name.getStyleClass().add("nameInput");
        name.textProperty().addListener((observable, oldValue, newValue) -> liquid.setName(newValue));
        return name;
    }

    /**
     * This method set ups spinners.
     * @param spinner Spinner to set up.
     */
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

    /**
     * This method creates spinner that represents amount of liquid in recipe. Used in main container.
     * @param liquid The fluid whose amount will be updated by this spinner.
     * @return Spinner that represents amount.
     */
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

    /**
     * This method creates spinner that represents percentage amount of alcohol of liquid in recipe. Used in main container.
     * @param liquid The fluid whose percentage amount of alcohol will be updated by this spinner.
     * @return Spinner that represents percentage amount of alcohol.
     */
    private static Spinner<Integer> getPercentageSpinner(Liquid liquid) {
        Spinner<Integer> percentage = new Spinner<>();
        percentage.getStyleClass().add("percentsSpinner");
        percentage.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, liquid.getPercent()));
        percentage.setEditable(true);
        percentage.valueProperty().addListener((observable, oldValue, newValue) -> liquid.setPercent(newValue));
        setUpSpinner(percentage);
        return percentage;
    }

    /**
     * This method creates color picker button that represents color of liquid. Used in main container.
     * @param liquid The fluid whose color will be updated by this color picker.
     * @return Color picker that represents color of alcohol.
     */
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

    /**
     * This method creates button that saves liquid in liquids list. Used in main container
     * @return Save button
     */
    private static Button getSaveButton() {
        Button save = new Button("Zapisz");
        save.getStyleClass().add("saveBtn");
        return save;
    }

    /**
     * This method creates button that deletes alcohol, liquid or information from main container.
     * Used in main container.
     * @return Delete button
     */
    private static Button getDeleteButton() {
        Button delete = new Button("Usuń");
        delete.getStyleClass().add("deleteBtn");
        return delete;
    }

    /**
     * This method creates panel that represents alcohol in main container.
     * @param liquid The liquid which is represented by the panel.
     * @return Alcohol panel
     */
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

    /**
     * This method creates panel that represents other liquids in main container.
     * @param liquid The liquid which is represented by the panel.
     * @return Other liquid panel
     */
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

    /**
     * This method creates panel that contains information.
     * @return Information panel
     */
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

    /**
     * Return built element of liquids list which is a HBox.
     * @param title Title that display on element.
     * @return Element of liquids list
     */
    private static HBox getListElement(String title) {
        HBox element = new HBox();

        Label nameLabel = new Label(title);
        nameLabel.setStyle("-fx-pref-width: 1000px");
        nameLabel.setCursor(Cursor.HAND);

        element.getChildren().addAll(nameLabel, getDeleteButton());
        element.setAlignment(Pos.CENTER);
        return element;
    }

    /**
     * Creates element of liquids list which is a alcohol.
     * @param name Name of alcohol
     * @param percents Percentage amount of alcohol
     * @return Element of liquids list
     */
    public static HBox getAlcoholListElement(String name, int percents) {
        return getListElement(name + " " + percents+"%");
    }

    /**
     * Creates element of liquids list which is a other liquid.
     * @param name Name of liquid
     * @return Element of liquids list
     */
    public static HBox getOtherListElement(String name) {
        return getListElement(name);
    }

    /**
     * This method return random color from basic {@link mainView.References#colors colors}
     * @return Random color
     */
    public static String getRandomColor() {
        int i = new Random().nextInt(colors.length);
        return colors[i];
    }

    /**
     * This method converts Color to String. String color will be in hexadecimal form e.g. <code>#f112a0</code>.
     * @param color Color to convert
     * @return Color in hexadecimal form
     */
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

    /**
     * This method converts String color (must be in hexadecimal form e.g. <code>#ef0011</code>) to Color.
     * @param color Color to convert
     * @return Converted color
     */
    public static Color toColor(String color) {
        return new Color(
                Integer.valueOf( color.substring( 1, 3 ), 16) / 256.0,
                Integer.valueOf( color.substring( 3, 5 ), 16) / 256.0,
                Integer.valueOf( color.substring( 5, 7 ), 16) / 256.0,
                1);
    }
}
