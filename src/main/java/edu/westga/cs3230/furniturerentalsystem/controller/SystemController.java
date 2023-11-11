package edu.westga.cs3230.furniturerentalsystem.controller;

import java.io.IOException;

import edu.westga.cs3230.furniturerentalsystem.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Abstract SystemController class
 *
 * @author Gavin Coppola
 * @version Fall 2023
 */
public abstract class SystemController {
    protected String loggedInUser;

    /**
     * Sets the loggedInLabel of controller to a user
     *
     * @param text the label to set
     */
    public void setLoggedInLabel(String text) {
    }
    
    public SystemController changePage(String pageName) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource(pageName));
		loader.load();
		Parent parent = loader.getRoot();
		Scene scene = new Scene(parent);
		Stage newStage = new Stage();

		newStage.setScene(scene);
		newStage.initModality(Modality.APPLICATION_MODAL);

		newStage.show();
		SystemController controller = loader.getController();
		controller.setLoggedInLabel(this.loggedInUser);
		return controller;
	}
}
