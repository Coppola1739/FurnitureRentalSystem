package edu.westga.cs3230.furniturerentalsystem.controller;

import java.io.IOException;
import java.util.ArrayList;

import edu.westga.cs3230.furniturerentalsystem.dao.RentalDao;
import edu.westga.cs3230.furniturerentalsystem.model.Member;
import edu.westga.cs3230.furniturerentalsystem.model.Rental;
import edu.westga.cs3230.furniturerentalsystem.model.RentalItem;
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
		this.handleRentalItemsDoubleClick();
		this.handleFurnitureCartDoubleClick();
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
					ArrayList<RentalItem> rentalItems = this.rentalDao.getRentalItemsFromRental(selectedRental.getRentalId());
					ArrayList<RentalItem> iteratedRentalItems = this.changeQuantityToOne(rentalItems);
					ObservableList<RentalItem> rentalItemsObservable = FXCollections.observableArrayList(iteratedRentalItems);
					this.selectedTransactionRentalItems.setItems(rentalItemsObservable);
				}
			}
		});
	}
	
	private void handleRentalItemsDoubleClick() {
		this.selectedTransactionRentalItems.setOnMouseClicked(event -> {
			if (event.getClickCount() == 2) {
				RentalItem selectedRentalItem = this.selectedTransactionRentalItems.getSelectionModel().getSelectedItem();
				if (selectedRentalItem != null) {
					this.returnFurnitureCartListView.getItems().add(selectedRentalItem);
					this.selectedTransactionRentalItems.getItems().remove(selectedRentalItem);
				}
			}
		});
	}
	
	private void handleFurnitureCartDoubleClick() {
		this.returnFurnitureCartListView.setOnMouseClicked(event -> {
			if (event.getClickCount() == 2) {
				RentalItem selectedRentalItem = this.returnFurnitureCartListView.getSelectionModel().getSelectedItem();
				if (selectedRentalItem != null) {
					this.returnFurnitureCartListView.getItems().remove(selectedRentalItem);
					this.selectedTransactionRentalItems.getItems().add(selectedRentalItem);
				}
			}
		});
	}
	
	private void checkForDuplicates() {
		
	}
	
	private ArrayList<RentalItem> changeQuantityToOne(ArrayList<RentalItem> listView) {
		ArrayList<RentalItem> splitItems = new ArrayList<RentalItem>();
		int quantity = 0;
		for (RentalItem item:listView) {
			quantity = item.getQuantity();
			for (int i = 0; i < quantity; i++) {
				item.setQuantity(1);
				splitItems.add(item);
			}
		}
		return splitItems;
	}
}

