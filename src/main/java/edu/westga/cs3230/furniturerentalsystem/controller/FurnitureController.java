package edu.westga.cs3230.furniturerentalsystem.controller;

import java.io.IOException;
import java.util.ArrayList;

import edu.westga.cs3230.furniturerentalsystem.Main;
import edu.westga.cs3230.furniturerentalsystem.dao.FurnitureDao;
import edu.westga.cs3230.furniturerentalsystem.model.Furniture;
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
	
	private FurnitureDao furnitureDao;
	
	@FXML
	private Label furnitureUserNameLabel;
	
    @FXML
    private Button backButton;

    @FXML
    private TextField furnitureIDSearchBox;

    @FXML
    private ListView<Furniture> furnitureListView;

    @FXML
    private ComboBox<String> furnitureStyleComboBox;

    @FXML
    private ComboBox<String> furnitureCategoryComboBox;

    @FXML
    private Button searchButton;

    
    @FXML
    void initialize() {
    	this.populateStyleComboBox();
    	this.populateTypeComboBox();
    	this.furnitureDao = new FurnitureDao();
    	this.loadFurnitureListView(this.furnitureDao.getAllFurniture());
    	this.addListenerForStyleComboBox();
    	this.addListenerForCategoryComboBox();
    }
    private void populateTypeComboBox() {
		this.furnitureCategoryComboBox.setItems(FXCollections.observableArrayList("Cabinet","Sofa","Chair","Table"));
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
    	this.furnitureStyleComboBox.getValue();
    	this.furnitureCategoryComboBox.getValue();
    }
    
    private void loadFurnitureListView(ArrayList<Furniture> allFurniture) {
    	this.furnitureListView.setItems(FXCollections.observableArrayList(allFurniture));
    }
    
    
    private void addListenerForStyleComboBox() {
    furnitureStyleComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue == null || newValue.isEmpty()) {
            this.loadFurnitureListView(this.furnitureDao.getAllFurniture());
        } else {
            this.loadFurnitureListView(this.furnitureDao.getFurnitureByStyle(newValue));
        }
    });
    }
    
    private void addListenerForCategoryComboBox() {
        furnitureCategoryComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                this.loadFurnitureListView(this.furnitureDao.getAllFurniture());
            } else {
                // Filter the ListView items to only contain strings containing the ComboBox value
                this.loadFurnitureListView(this.furnitureDao.getFurnitureByCategory(newValue));
            }
        });
    }
}