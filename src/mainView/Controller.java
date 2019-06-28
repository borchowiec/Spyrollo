package mainView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import static mainView.Layout.*;

public class Controller implements Initializable {

    public VBox mixPanel;
    public Label amountLabel;
    public Label percentageLabel;
    public VBox liquidsList;
    public TextField titleInput;
    public Label msgLabel;
    @FXML
    private VBox mainContainer;

    private List<Liquid> liquids = new LinkedList<>();
    private double amountOfLiquids = 0;

    private JsonHandler jsonHandler;

    public Controller() {
        jsonHandler = new JsonHandler();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        jsonHandler.loadLiquids(this);
    }

    private void setUpElement(Liquid liquid, HBox panel) {
        liquids.add(liquid);
        mainContainer.getChildren().add(panel);
        panel.lookup(".deleteBtn").setOnMouseClicked(event -> {
            liquids.remove(liquid);
            mainContainer.getChildren().remove(panel);
            refreshInfo();
            refreshMixPanel();
        });

        liquid.amountProperty().addListener((observable, oldValue, newValue) -> {
            refreshInfo();
            refreshMixPanel();
        });

        refreshInfo();
        refreshMixPanel();

        panel.lookup(".up").setOnMouseClicked(event -> {
            int i1 = mainContainer.getChildren().indexOf(panel);
            if (i1 > 0) {
                mainContainer.getChildren().remove(i1);
                mainContainer.getChildren().add(i1 - 1, panel);
            }

            int i2 = liquids.indexOf(liquid);
            if (i2 > 0 && mainContainer.getChildren().get(i1).lookup(".infoInput") == null) {
                liquids.remove(i2);
                liquids.add(i2 - 1, liquid);
                refreshMixPanel();
            }
        });
        panel.lookup(".down").setOnMouseClicked(event -> {
            int i1 = mainContainer.getChildren().indexOf(panel);
            if (i1 < mainContainer.getChildren().size() - 1) {
                mainContainer.getChildren().remove(i1);
                mainContainer.getChildren().add(i1 + 1, panel);
            }

            int i2 = liquids.indexOf(liquid);
            if (i2 < liquids.size() - 1 && mainContainer.getChildren().get(i1).lookup(".infoInput") == null) {
                liquids.remove(i2);
                liquids.add(i2 + 1, liquid);
                refreshMixPanel();
            }
        });
    }

    public void addAlcohol() {
        Liquid liquid = new Liquid("Alkohol", 100, 0, Layout.getRandomColor());
        addAlcohol(liquid);
    }

    public void addAlcohol(Liquid liquid) {
        HBox panel = Layout.getAlcoholPanel(liquid);
        liquid.percentProperty().addListener((observable, oldValue, newValue) -> refreshInfo());
        panel.lookup(".saveBtn").setOnMouseClicked(event -> {
            addAlcoholToLiquidList(liquid);
            jsonHandler.addAlcoholToLiquids(
                    ((TextField)panel.lookup(".nameInput")).getText(),
                    (int) ((Spinner)panel.lookup(".percentsSpinner")).getValue(),
                    Layout.toRGB(((ColorPicker)panel.lookup(".colorPicker")).getValue())
            );
        });
        setUpElement(liquid, panel);
    }

    public void addOther() {
        Liquid liquid = new Liquid("Napój", 100, 0, Layout.getRandomColor());
        addOther(liquid);
    }

    public void addOther(Liquid liquid) {
        HBox panel = Layout.getOtherPanel(liquid);
        panel.lookup(".saveBtn").setOnMouseClicked(event -> {
            addOtherToLiquidList(liquid);
            jsonHandler.addOtherToLiquids(
                    ((TextField)panel.lookup(".nameInput")).getText(),
                    Layout.toRGB(((ColorPicker)panel.lookup(".colorPicker")).getValue())
            );
        });
        setUpElement(liquid, panel);
    }

    public void addInfo() {
        addInfo("Informacja");
    }

    private void addInfo(String content) {
        HBox panel = Layout.getInfoPanel();
        mainContainer.getChildren().add(panel);
        panel.lookup(".deleteBtn").setOnMouseClicked(event -> mainContainer.getChildren().remove(panel));
        ((TextField)panel.lookup(".infoInput")).setText(content);

        panel.lookup(".up").setOnMouseClicked(event -> {
            int i1 = mainContainer.getChildren().indexOf(panel);
            if (i1 > 0) {
                mainContainer.getChildren().remove(i1);
                mainContainer.getChildren().add(i1 - 1, panel);
            }
        });
        panel.lookup(".down").setOnMouseClicked(event -> {
            int i1 = mainContainer.getChildren().indexOf(panel);
            if (i1 < mainContainer.getChildren().size() - 1) {
                mainContainer.getChildren().remove(i1);
                mainContainer.getChildren().add(i1 + 1, panel);
            }
        });
    }

    public void addAlcoholToLiquidList(Liquid liquid) {
        HBox element = Layout.getAlcoholListElement(liquid.getName(), liquid.getPercent());
        liquidsList.getChildren().add(element);

        element.lookup(".label").setOnMouseClicked(event -> addAlcohol(liquid.clone()));
        element.lookup(".deleteBtn").setOnMouseClicked(event -> {
            int i = liquidsList.getChildren().indexOf(element);
            jsonHandler.removeFromLiquidsList(i);
            liquidsList.getChildren().remove(element);
        });
    }

    public void addOtherToLiquidList(Liquid liquid) {
        HBox element = Layout.getOtherListElement(liquid.getName());
        liquidsList.getChildren().add(element);

        element.lookup(".label").setOnMouseClicked(event -> addOther(liquid.clone()));
        element.lookup(".deleteBtn").setOnMouseClicked(event -> {
            int i = liquidsList.getChildren().indexOf(element);
            jsonHandler.removeFromLiquidsList(i);
            liquidsList.getChildren().remove(element);
        });
    }

    private void refreshInfo() {
        double alcohol = 0;
        double other = 0;

        if (liquids.size() == 0) {
            amountLabel.setText(0 + " ml");
            percentageLabel.setText(0 + " %");
            amountOfLiquids = 0;
        }
        else {
            for (Liquid l : liquids) {
                double tempAlc = l.getAmount() * (l.getPercent() / 100.0);
                alcohol += tempAlc;
                other += l.getAmount() - tempAlc;
            }

            amountOfLiquids = alcohol + other;
            amountLabel.setText(amountOfLiquids + " ml");
            percentageLabel.setText(Math.round((alcohol / (alcohol + other)) * 10000.0) / 100.0 + " %");
        }
    }

    private void refreshMixPanel() {
        mixPanel.getChildren().clear();
        GridPane gridPane = new GridPane();

        for (int i = 0; i < liquids.size(); i++) {
            Liquid l = liquids.get(i);
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(l.getAmount() / amountOfLiquids * 100);

            gridPane.getRowConstraints().add(row);

            Pane pane = new Pane();
            pane.setStyle("-fx-background-color: " + l.getColor());
            pane.setPrefHeight(1000);
            pane.setPrefWidth(1000);

            l.colorProperty().addListener((observable, oldValue, newValue) ->
                    pane.setStyle("-fx-background-color: " + l.getColor()));

            Label nameLabel = new Label(l.getName());
            nameLabel.textProperty().bind(l.nameProperty());
            nameLabel.setStyle("-fx-text-fill: #ffffff");
            pane.getChildren().add(nameLabel);

            gridPane.add(pane, 0, i);
        }

        mixPanel.getChildren().add(gridPane);
    }

    public void saveRecipe() {
        try {
            jsonHandler.saveRecipe(titleInput.getText(), mainContainer.getChildren());
            showMsg("Zapisano!");
        } catch (IOException e) {
            showMsg("Nie można zapisać pliku!");
            e.printStackTrace();
        }
    }

    public void loadRecipe() {
        JsonObject json = RecipeChooser.display();
        titleInput.setText(json.get("title").getAsString());
        JsonArray elements = json.getAsJsonArray("elements");

        mainContainer.getChildren().clear();
        mixPanel.getChildren().clear();
        liquids.clear();

        for (JsonElement el : elements) {
            JsonObject temp = el.getAsJsonObject();
            Liquid liquid;
            switch (temp.get("type").getAsInt()) {
                case ALCOHOL:
                    liquid = new Liquid(
                            temp.get("name").getAsString(),
                            temp.get("amount").getAsInt(),
                            temp.get("percents").getAsInt(),
                            temp.get("color").getAsString()
                    );
                    addAlcohol(liquid);
                    break;
                case OTHER:
                    liquid = new Liquid(
                            temp.get("name").getAsString(),
                            temp.get("amount").getAsInt(),
                            0,
                            temp.get("color").getAsString()
                    );
                    addOther(liquid);
                    break;
                case INFO:
                    addInfo(temp.get("content").getAsString());
                    break;
            }
        }
    }

    public void reset() {
        titleInput.setText("Untitled");
        liquids.clear();
        mixPanel.getChildren().clear();
        mainContainer.getChildren().clear();
        refreshInfo();
    }

    private void showMsg(String msg) {
        msgLabel.setText(msg);
        new Timeline(new KeyFrame(
                Duration.millis(2500),
                ae -> msgLabel.setText(" "))).play();
    }
}