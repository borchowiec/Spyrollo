package mainView.windows;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;


class BasicWindow {
    static Stage getBasicWindow(String title) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(450);
        return window;
    }

    static void setScene(Stage window, Parent layout) {
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}
