package mainView;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.util.LinkedList;
import java.util.List;

public class Controller {

    public VBox mixPanel;
    public Label amountLabel;
    public Label percentageLabel;
    @FXML
    private VBox mainContainer;

    private List<Liquid> liquids = new LinkedList<>();
    private double amountOfLiquids = 0;

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
        Liquid liquid = new Liquid();
        HBox panel = Layout.getAlcoholPanel(liquid);
        liquid.percentProperty().addListener((observable, oldValue, newValue) -> refreshInfo());
        setUpElement(liquid, panel);
    }

    public void addOther() {
        Liquid liquid = new Liquid();
        HBox panel = Layout.getOtherPanel(liquid);
        setUpElement(liquid, panel);
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
            percentageLabel.setText((alcohol / (alcohol + other)) * 100 + " %");
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
