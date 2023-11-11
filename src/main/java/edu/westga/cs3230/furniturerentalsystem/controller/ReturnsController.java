package edu.westga.cs3230.furniturerentalsystem.controller;

import java.io.IOException;
import java.util.ArrayList;

import edu.westga.cs3230.furniturerentalsystem.dao.RentalDao;
import edu.westga.cs3230.furniturerentalsystem.model.Member;
import edu.westga.cs3230.furniturerentalsystem.model.Rental;
import edu.westga.cs3230.furniturerentalsystem.model.RentalItem;
import edu.westga.cs3230.furniturerentalsystem.model.Return;
import edu.westga.cs3230.furniturerentalsystem.util.Constants;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
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
	private ListView<RentalItem> selectedTransactionRentalItems;
	
    @FXML
    private ListView<RentalItem> returnFurnitureCartListView;

    @FXML
    private ListView<RentalItem> returnsListView;
	
    @FXML
    private TextField lateFeeOwedTextArea;
    
	@FXML
	void returnSelectedFurniture() {
		
	}
		
	
	@FXML
	void initialize() {
		this.rentalDao = new RentalDao();
		this.currMember = new Member();
		this.handleRentalListDoubleClick();
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
		this.rentalsListView.setItems(FXCollections.observableArrayList(this.rentals));
	}
	
	private void handleRentalListDoubleClick() {
		this.rentalsListView.setOnMouseClicked(event -> {
			if (event.getClickCount() == 2) {
				Rental selectedRental = this.rentalsListView.getSelectionModel().getSelectedItem();
				if (selectedRental != null) {
					ObservableList<RentalItem> rentalItems = FXCollections.observableArrayList(this.rentalDao.getRentalItemsFromRental(selectedRental.getRentalId()));
					this.selectedTransactionRentalItems.setItems(rentalItems);
				}
			}
		});
	}
}

