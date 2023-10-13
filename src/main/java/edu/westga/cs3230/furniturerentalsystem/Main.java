package edu.westga.cs3230.furniturerentalsystem;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Entry point for the collectible manager application
 *
 * @author Gavin Coppola
 * @version Fall 2023
 */
public class Main extends Application {

    private static final String WINDOW_TITLE = "Furniture Rental System";
    private static final String GUI_FXML = "/view/Login.fxml";

    @Override
    public void start(Stage primaryStage) {
        try {
            System.out.println(getClass().getResource(GUI_FXML));
			Parent root = FXMLLoader.load(getClass().getResource(GUI_FXML));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle(WINDOW_TITLE);
            primaryStage.show();
        } catch (IllegalStateException | IOException anException) {
            anException.printStackTrace();
        }
    }

    /**
     * Launches the application
     *
     * @param args not used
     * @pre none
     * @post none
     */
    public static void main(String[] args) {
        launch(args);
    }

}
