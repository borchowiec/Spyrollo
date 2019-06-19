package mainView;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public VBox mixPanel;
    public Label amountLabel;
    public Label percentageLabel;
    public VBox liquidsList;
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
        jsonHandler.load(this);
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
            jsonHandler.addAlcohol(
                    ((TextField)panel.lookup(".nameInput")).getText(),
                    (int) ((Spinner)panel.lookup(".percentageSpinner")).getValue(),
                    Layout.toRGB((Color) ((Button)panel.lookup(".colorBtn")).getBackground().getFills().get(0).getFill())
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
            jsonHandler.addOther(
                    ((TextField)panel.lookup(".nameInput")).getText(),
                    Layout.toRGB((Color) ((Button)panel.lookup(".colorBtn")).getBackground().getFills().get(0).getFill())
            );
        });
        setUpElement(liquid, panel);
    }

    public void addAlcoholToLiquidList(Liquid liquid) {
        HBox element = Layout.getAlcoholListElement(liquid.getName(), liquid.getPercent());
        liquidsList.getChildren().add(element);

        element.lookup(".label").setOnMouseClicked(event -> addAlcohol(liquid.clone()));
        element.lookup(".deleteBtn").setOnMouseClicked(event -> {
            int i = liquidsList.getChildren().indexOf(element);
            jsonHandler.removeFromLiquidList(i);
            liquidsList.getChildren().remove(element);
        });
    }

    public void addOtherToLiquidList(Liquid liquid) {
        HBox element = Layout.getOtherListElement(liquid.getName());
        liquidsList.getChildren().add(element);

        element.lookup(".label").setOnMouseClicked(event -> addOther(liquid.clone()));
        element.lookup(".deleteBtn").setOnMouseClicked(event -> {
            int i = liquidsList.getChildren().indexOf(element);
            jsonHandler.removeFromLiquidList(i);
            liquidsList.getChildren().remove(element);
        });
    }

    public void addInfo() {
        HBox panel = Layout.getInfoPanel();
        mainContainer.getChildren().add(panel);
        panel.lookup(".deleteBtn").setOnMouseClicked(event -> mainContainer.getChildren().remove(panel));
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

            Label nameLabel = new Label(l.getName());
            nameLabel.textProperty().bind(l.nameProperty());
            nameLabel.setStyle("-fx-text-fill: #ffffff");
            pane.getChildren().add(nameLabel);

            gridPane.add(pane, 0, i);
        }

        mixPanel.getChildren().add(gridPane);
    }
}
