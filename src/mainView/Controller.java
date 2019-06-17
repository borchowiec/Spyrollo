package mainView;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

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

    public void addAlcohol() {
        Liquid liquid = new Liquid();
        mainContainer.getChildren().add(Layout.getAlcoholPanel(liquid));
        liquids.add(liquid);
        liquid.amountProperty().addListener((observable, oldValue, newValue) -> {
            refreshInfo();
            refreshMixPanel();
        });
        liquid.percentProperty().addListener((observable, oldValue, newValue) -> refreshInfo());
        refreshInfo();
        refreshMixPanel();
    }

    public void addOther() {
        Liquid liquid = new Liquid();
        mainContainer.getChildren().add(Layout.getOtherPanel(liquid));
        liquids.add(liquid);
        liquid.amountProperty().addListener((observable, oldValue, newValue) -> {
            refreshInfo();
            refreshMixPanel();
        });
        refreshInfo();
        refreshMixPanel();
    }

    public void addInfo() {
        mainContainer.getChildren().add(Layout.getInfoPanel());
    }

    private void refreshInfo() {
        double alcohol = 0;
        double other = 0;

        for (Liquid l : liquids) {
            double tempAlc = l.getAmount() * (l.getPercent() / 100.0);
            alcohol += tempAlc;
            other += l.getAmount() - tempAlc;
        }

        amountLabel.setText((alcohol + other) + " ml");
        percentageLabel.setText((alcohol / (alcohol + other))*100 + " %");

        amountOfLiquids = alcohol + other;
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
