package mainView.windows;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * This class contains helpers methods for other windows.
 * @author Patryk Borchowiec
 */
class BasicWindow {
    /**
     * Creates and set ups a window.
     * @param title Title of window
     * @return Built window
     */
    static Stage getBasicWindow(String title) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(450);
        return window;
    }

    /**
     * This method set scenes of window.
     * @param window Window in which you want to set the scene
     * @param layout Layout that you want to set in scene
     */
    static void setScene(Stage window, Parent layout) {
        Scene scene = new Scene(layout);
        scene.getStylesheets().add("mainView/style/style.css");
        window.setScene(scene);
        window.showAndWait();
    }
}
