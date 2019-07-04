package mainView;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mainView.windows.ConfirmWindow;

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
        primaryStage.setOnCloseRequest(event -> {
            int option = ConfirmWindow.display("Wyjście", "Czy na pewno chcesz zamknąć program? Niezapisane zmiany mogą zostać utracone.");
            if (option == ConfirmWindow.NO)
                event.consume();
        });
        root.getStylesheets().add("mainView/style/style.css");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
