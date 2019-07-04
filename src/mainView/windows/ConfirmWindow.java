package mainView.windows;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * This class is responsible for asking user if he is sure of his activities.
 * @author Patryk Borchowiec
 */
public class ConfirmWindow {

    public static final int NO = 0;
    public static final int YES = 1;

    private static int option = NO;

    /**
     * This method creates, displays window and waits for choosing options.
     * @return Chosen properties
     */
    public static int display(String title, String question) {
        option = NO;
        Stage window = BasicWindow.getBasicWindow(title);

        VBox layout = new VBox(5);
        layout.setPadding(new Insets(10));
        Label label = new Label(question);
        HBox buttonPanel = new HBox(10);
        buttonPanel.setAlignment(Pos.CENTER);

        Button yes = new Button("Tak");
        Button no = new Button("Nie");

        yes.setOnAction(event -> {
            option = YES;
            window.close();
        });

        no.setOnAction(event -> {
            option = NO;
            window.close();
        });

        buttonPanel.getChildren().addAll(yes, no);
        layout.getChildren().addAll(label, buttonPanel);
        BasicWindow.setScene(window, layout);
        return option;
    }

}
