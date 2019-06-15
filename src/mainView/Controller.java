package mainView;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class Controller {

    @FXML
    private VBox mainContainer;

    public void addAlcohol() {
         mainContainer.getChildren().add(Layout.getAlcoholPanel());
    }

    public void addOther() {
        mainContainer.getChildren().add(Layout.getOtherPanel());
    }

    public void addInfo() {
        mainContainer.getChildren().add(Layout.getInfoPanel());
    }
}
