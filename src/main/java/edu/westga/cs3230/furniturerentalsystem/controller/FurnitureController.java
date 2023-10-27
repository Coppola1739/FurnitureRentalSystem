package edu.westga.cs3230.furniturerentalsystem.controller;

import java.io.IOException;

import edu.westga.cs3230.furniturerentalsystem.Main;
import edu.westga.cs3230.furniturerentalsystem.util.Constants;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FurnitureController extends SystemController{

	@FXML
	private Label furnitureUserNameLabel;
	
    @FXML
    private Button backButton;

    @FXML
    private TextField furnitureIDSearchBox;

    @FXML
    private ListView<String> furnitureListView;

    @FXML
    private ComboBox<String> furnitureStyleComboBox;

    @FXML
    private ComboBox<String> furnitureTypeComboBox;

    @FXML
    private Button searchButton;

    
    @FXML
    void initialize() {
    	this.populateStyleComboBox();
    	this.populateTypeComboBox();
    }
    private void populateTypeComboBox() {
		this.furnitureStyleComboBox.setItems(FXCollections.observableArrayList("Cabinet","Sofa","Chair","Table"));
	}
	private void populateStyleComboBox() {
		this.furnitureStyleComboBox.setItems(FXCollections.observableArrayList("Modern","Traditional","Rustic","Scandinavian"));
	}
	public void setLoggedInLabel(String username) {
        super.loggedInUser = username;
        this.furnitureUserNameLabel.textProperty().set("Logged In: " + super.loggedInUser);
    }
    
    @FXML
    void backToLandingPage(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource(Constants.HOME_PAGE_FXML));
        loader.load();
        Parent parent = loader.getRoot();
        Scene scene = new Scene(parent);
        Stage newStage = new Stage();
        
        SystemController controller = loader.getController();
        controller.setLoggedInLabel(super.loggedInUser);

        newStage.setScene(scene);
        newStage.initModality(Modality.APPLICATION_MODAL);

        newStage.show();
        Stage thisStage = (Stage) this.backButton.getScene().getWindow();
        thisStage.close();
    }

    @FXML
    void searchFurniture(ActionEvent event) {
    	
    }

}