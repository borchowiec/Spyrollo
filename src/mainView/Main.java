package mainView;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class starts application.
 * @author Patryk Borchowiec
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("mainView.fxml"));
        primaryStage.setTitle("Spyrollo");
        primaryStage.setScene(new Scene(root, 1200, 800));
        root.getStylesheets().add("mainView/style/style.css");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
