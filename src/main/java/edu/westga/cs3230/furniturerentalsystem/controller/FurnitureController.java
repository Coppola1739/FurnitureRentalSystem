package edu.westga.cs3230.furniturerentalsystem.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class FurnitureController extends SystemController{

    @FXML
    private Button backButton;

    @FXML
    private TextField furnitureIDSearchBox;

    @FXML
    private ListView<?> furnitureListView;

    @FXML
    private ComboBox<?> furnitureStyleDropdownBox;

    @FXML
    private ComboBox<?> furnitureTypeDropdownBox;

    @FXML
    private Button searchButton;

    @FXML
    void backToLandingPage(ActionEvent event) {

    }

    @FXML
    void searchFurniture(ActionEvent event) {

    }

}