package mainView;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
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

    public void addAlcohol() {
        Liquid liquid = new Liquid();
        mainContainer.getChildren().add(Layout.getAlcoholPanel(liquid));
        liquids.add(liquid);
        liquid.amountProperty().addListener((observable, oldValue, newValue) -> refresh());
        liquid.percentProperty().addListener((observable, oldValue, newValue) -> refresh());
        refresh();
    }

    public void addOther() {
        Liquid liquid = new Liquid();
        mainContainer.getChildren().add(Layout.getOtherPanel(liquid));
        liquids.add(liquid);
        liquid.amountProperty().addListener((observable, oldValue, newValue) -> refresh());
        refresh();
    }

    public void addInfo() {
        mainContainer.getChildren().add(Layout.getInfoPanel());
    }

    private void refresh() {
        double alcohol = 0;
        double other = 0;

        for (Liquid l : liquids) {
            double tempAlc = l.getAmount() * (l.getPercent() / 100.0);
            alcohol += tempAlc;
            other += l.getAmount() - tempAlc;
        }

        amountLabel.setText((alcohol + other) + " ml");
        percentageLabel.setText((alcohol / (alcohol + other))*100 + " %");
    }
}
