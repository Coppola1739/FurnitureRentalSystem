package edu.westga.cs3230.furniturerentalsystem.controller;

import java.io.IOException;

import edu.westga.cs3230.furniturerentalsystem.model.Member;
import edu.westga.cs3230.furniturerentalsystem.model.PersonalInformation;
import edu.westga.cs3230.furniturerentalsystem.model.RentalInfo;
import edu.westga.cs3230.furniturerentalsystem.util.Constants;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import lombok.NonNull;

public class ReturnsController extends SystemController {

	@FXML
	private Button returnFurnitureButton;
	
	@FXML
	private Button returnsPageBackButton;
	
	@FXML
	private ListView<RentalInfo> rentalsListView;
	
	@FXML
	private Label loggedInLabel;
	
	private Member currMember;
	
	@FXML
	void returnSelectedFurniture() {
		
	}
		
	@FXML
	void initialize() {
		
	}
	
	@FXML
	void backToTransactionsPage() throws IOException {
		changePage(Constants.TRANSACTION_PAGE_FXML);
		Stage stage = (Stage) this.returnsPageBackButton.getScene().getWindow();
		stage.close();
	}
	
	@Override
	public void setLoggedInLabel(String username) {
		super.loggedInUser = username;
		this.loggedInLabel.textProperty().set("Logged In: " + super.loggedInUser);
	}

	public void setSelectedUser(Member member) {
		this.currMember = member;
		this.populateRentalsListView(this.currMember.getPInfo());
	}

	private void populateRentalsListView(@NonNull PersonalInformation pInfo) {
		// TODO Auto-generated method stub
		
	}
}

