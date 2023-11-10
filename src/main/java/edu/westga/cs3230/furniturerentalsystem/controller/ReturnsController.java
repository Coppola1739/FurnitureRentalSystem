package edu.westga.cs3230.furniturerentalsystem.controller;

import edu.westga.cs3230.furniturerentalsystem.model.RentalInfo;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class ReturnsController extends SystemController {

	@FXML
	private Button returnFurnitureButton;
	
	@FXML
	private Button returnsPageBackButton;
	
	@FXML
	private ListView<RentalInfo> rentalsListView;
	
	@FXML
	void returnSelectedFurniture() {
		
	}
	
	@FXML
	void backToTransactionsPage() {
		
	}
}

