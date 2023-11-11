package edu.westga.cs3230.furniturerentalsystem.controller;

import java.io.IOException;
import java.util.ArrayList;

import edu.westga.cs3230.furniturerentalsystem.dao.RentalDao;
import edu.westga.cs3230.furniturerentalsystem.model.Member;
import edu.westga.cs3230.furniturerentalsystem.model.Rental;
import edu.westga.cs3230.furniturerentalsystem.util.Constants;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class ReturnsController extends SystemController {

	private RentalDao rentalDao;
	private Member currMember;
	private ArrayList<Rental> rentals;
	@FXML
	private Button returnFurnitureButton;
	
	@FXML
	private Button returnsPageBackButton;
	
	@FXML
	private ListView<Rental> rentalsListView;
	
	@FXML
	private Label loggedInLabel;
	
	
	@FXML
	void returnSelectedFurniture() {
		
	}
		
	@FXML
	void initialize() {
		this.rentalDao = new RentalDao();
		this.currMember = new Member();
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
		this.populateRentalsListView(member.getMemberId());
	}

	private void populateRentalsListView(String memberId) {
		ArrayList<Rental> allRentalsForCustomer = this.rentalDao.getAllRentalsForMember(currMember.getMemberId());
		this.rentals = allRentalsForCustomer;
		System.out.println(allRentalsForCustomer);
		this.rentalsListView.setItems(FXCollections.observableArrayList(this.rentals));
	}
}

