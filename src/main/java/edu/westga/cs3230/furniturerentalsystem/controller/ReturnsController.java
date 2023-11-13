package edu.westga.cs3230.furniturerentalsystem.controller;

import java.io.IOException;
import java.util.ArrayList;

import edu.westga.cs3230.furniturerentalsystem.dao.EmployeeDao;
import edu.westga.cs3230.furniturerentalsystem.dao.RentalDao;
import edu.westga.cs3230.furniturerentalsystem.dao.ReturnDao;
import edu.westga.cs3230.furniturerentalsystem.model.Employee;
import edu.westga.cs3230.furniturerentalsystem.model.Member;
import edu.westga.cs3230.furniturerentalsystem.model.Rental;
import edu.westga.cs3230.furniturerentalsystem.model.RentalItem;
import edu.westga.cs3230.furniturerentalsystem.model.Return;
import edu.westga.cs3230.furniturerentalsystem.model.ReturnItem;
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

	private EmployeeDao employeeDao;
	private RentalDao rentalDao;
	private Member currMember;
	private ArrayList<Rental> rentals;
	private ReturnDao returnDao;
	private Employee currEmployee;
	
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
	private ListView<Return> returnsListView;

	@FXML
	private TextField lateFeeOwedTextArea;

	@FXML
	void returnSelectedFurniture() throws Exception {
		String employeeNum = EmployeeDao.getEmployeeNumByUsername(loggedInUser);
		ReturnDao.addReturn(this.currMember.getMemberId(), employeeNum);
		this.arrangeCartItemsForReturn();
		
	}

	private ArrayList<RentalItem> arrangeCartItemsForReturn() {
	    ArrayList<RentalItem> itemsBeingReturned = new ArrayList<>(this.returnFurnitureCartListView.getItems());
	    ArrayList<RentalItem> withCorrectQuantity = new ArrayList<>();

	    for (RentalItem itemOne : itemsBeingReturned) {
	        boolean found = false;

	        for (RentalItem itemTwo : withCorrectQuantity) {
	            if (itemOne.getFurnitureId().equals(itemTwo.getFurnitureId()) && itemOne.getRentalId().equals(itemTwo.getRentalId())) {
	                itemTwo.setQuantity(itemTwo.getQuantity() + 1);
	                found = true;
	                break;
	            }
	        }

	        if (!found) {
	            withCorrectQuantity.add(new RentalItem(itemOne.getRentalId(), itemOne.getFurnitureId(), itemOne.getQuantity(), itemOne.getCost()));
	        }
	    }
	    return withCorrectQuantity;
	}

	@FXML
	void initialize() {
		this.employeeDao = new EmployeeDao();
		this.returnDao = new ReturnDao();
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
					ArrayList<RentalItem> rentalItems = this.rentalDao
							.getRentalItemsFromRental(selectedRental.getRentalId());
					ArrayList<RentalItem> duplicatesRemovedList = this.checkForDuplicates(rentalItems);
					ArrayList<RentalItem> iteratedRentalItems = this.changeQuantityToOne(duplicatesRemovedList);
					ObservableList<RentalItem> rentalItemsObservable = FXCollections
							.observableArrayList(iteratedRentalItems);
					this.selectedTransactionRentalItems.setItems(rentalItemsObservable);
				}
			}
		});
	}

	private void handleRentalItemsDoubleClick() {
		this.selectedTransactionRentalItems.setOnMouseClicked(event -> {
			if (event.getClickCount() == 2) {
				RentalItem selectedRentalItem = this.selectedTransactionRentalItems.getSelectionModel()
						.getSelectedItem();
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

	/**
	 * This method goes through what is in your cart and removes duplicates if you reload the same rental.
	 * Because the return cart is only tentative until the transaction goes through, it is necessary
	 * to keep all the lists current. Without this method, changing to a transaction and changing back would
	 * cause duplicates to appear between your rentals and your cart.
	 * @param selectedTransactionItems
	 * @return
	 */
	private ArrayList<RentalItem> checkForDuplicates(ArrayList<RentalItem> selectedTransactionItems) {
		ArrayList<RentalItem> transactionDuplicates = new ArrayList<RentalItem>(selectedTransactionItems);
		ArrayList<RentalItem> cartDuplicates = new ArrayList<RentalItem>(this.returnFurnitureCartListView.getItems());
		for (RentalItem item : transactionDuplicates) {
			for (RentalItem fakeItem : cartDuplicates) {
				if (item.getRentalId().equals(fakeItem.getRentalId())
						&& item.getFurnitureId().equals(fakeItem.getFurnitureId())) {
					item.setQuantity(item.getQuantity() - 1);
					continue;
				}
			}
		}
		return transactionDuplicates;
	}

	/**
	 * Splits a rentalItem into individual items, i.e., if a rentalItem has a quantity of 5,
	 * this method makes it appear as 5 individual items of quantity 1.
	 * @param listView
	 * @return
	 */
	private ArrayList<RentalItem> changeQuantityToOne(ArrayList<RentalItem> listView) {
		ArrayList<RentalItem> splitItems = new ArrayList<RentalItem>();
		int quantity = 0;
		for (RentalItem item : listView) {
			quantity = item.getQuantity();
			for (int i = 0; i < quantity; i++) {
				item.setQuantity(1);
				splitItems.add(item);
			}
		}
		return splitItems;
	}

}
