package edu.westga.cs3230.furniturerentalsystem.controller;

import edu.westga.cs3230.furniturerentalsystem.Main;
import edu.westga.cs3230.furniturerentalsystem.dao.EmployeeDao;
import edu.westga.cs3230.furniturerentalsystem.dao.FurnitureDao;
import edu.westga.cs3230.furniturerentalsystem.model.Employee;
import edu.westga.cs3230.furniturerentalsystem.model.Furniture;
import edu.westga.cs3230.furniturerentalsystem.util.Constants;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class FurnitureController extends SystemController {
	
	private static final String NO_SELECTION = "-No Selection-";

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
    	this.furnitureStyleComboBox.setValue(NO_SELECTION);
    	this.furnitureCategoryComboBox.setValue(NO_SELECTION);
    	this.addListenerForStyleComboBox();
    	this.addListenerForCategoryComboBox();
    }
    private void populateTypeComboBox() {
		this.furnitureCategoryComboBox.setItems(FXCollections.observableArrayList("Cabinet", "Sofa", "Chair", "Table", NO_SELECTION));
	}
	private void populateStyleComboBox() {
		this.furnitureStyleComboBox.setItems(FXCollections.observableArrayList("Modern", "Traditional", "Rustic", "Scandinavian", NO_SELECTION));
	}
    public void setLoggedInLabel(String username) {
        super.loggedInUser = username;
        Employee employee = EmployeeDao.getEmployeeByUsername(username).get(0);
        this.furnitureUserNameLabel.textProperty().set("Logged In: " + employee.getPInfo().getFirstName() + " " + employee.getPInfo().getLastName());
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
    	this.loadFurnitureListView(this.furnitureDao.getFurnitureById(this.furnitureIDSearchBox.getText()));
    }
    
    private void loadFurnitureListView(ArrayList<Furniture> allFurniture) {
    	this.furnitureListView.setItems(FXCollections.observableArrayList(allFurniture));
    }
    
    
    private void addListenerForStyleComboBox() {
    	this.furnitureStyleComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
    		String furnitureCategory = this.furnitureCategoryComboBox.getValue();
    		if (newValue.equals(NO_SELECTION) && (furnitureCategory.equals(NO_SELECTION))) {
	            this.loadFurnitureListView(this.furnitureDao.getAllFurniture());
	        } else if (newValue.equals(NO_SELECTION) && (!furnitureCategory.equals(NO_SELECTION))) {
	        	this.loadFurnitureListView(this.furnitureDao.getFurnitureByCategory(furnitureCategory));
	        	
	        } else if (!newValue.equals(NO_SELECTION) && (!furnitureCategory.equals(NO_SELECTION))) {
	        	this.loadFurnitureListView(this.furnitureDao.getFurnitureByStyleAndCategory(newValue, this.furnitureCategoryComboBox.getValue()));

    		}    		else {
	            this.loadFurnitureListView(this.furnitureDao.getFurnitureByStyle(newValue));
	        }
    	});
    }
    
    private void addListenerForCategoryComboBox() {
        this.furnitureCategoryComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
        	String furnitureStyle = this.furnitureStyleComboBox.getValue();
            if (newValue.equals(NO_SELECTION) && (furnitureStyle.equals(NO_SELECTION))) {
                this.loadFurnitureListView(this.furnitureDao.getAllFurniture());
            } else if (newValue.equals(NO_SELECTION) && (!furnitureStyle.equals(NO_SELECTION))) {
	        	this.loadFurnitureListView(this.furnitureDao.getFurnitureByStyle(furnitureStyle));
	        } else if (!newValue.equals(NO_SELECTION) && (!furnitureStyle.equals(NO_SELECTION))) {
	        	this.loadFurnitureListView(this.furnitureDao.getFurnitureByStyleAndCategory(furnitureStyle, newValue));
    		} else {
	            this.loadFurnitureListView(this.furnitureDao.getFurnitureByCategory(newValue));
	        }
        });
    }
}