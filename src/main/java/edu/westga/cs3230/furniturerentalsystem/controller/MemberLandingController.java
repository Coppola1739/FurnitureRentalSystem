package edu.westga.cs3230.furniturerentalsystem.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import edu.westga.cs3230.furniturerentalsystem.Main;
import edu.westga.cs3230.furniturerentalsystem.dao.MemberDao;
import edu.westga.cs3230.furniturerentalsystem.dao.RentalDao;
import edu.westga.cs3230.furniturerentalsystem.model.Member;
import edu.westga.cs3230.furniturerentalsystem.model.Rental;
import edu.westga.cs3230.furniturerentalsystem.model.RentalItem;
import edu.westga.cs3230.furniturerentalsystem.model.Return;
import edu.westga.cs3230.furniturerentalsystem.util.Constants;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MemberLandingController extends SystemController {

	private Member currMember;
	private ArrayList<Rental> rentals;
	private ArrayList<Return> returns;
	private RentalDao rentalDao;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private ListView<Rental> rentalsListView;

	@FXML
	private ListView<RentalItem> rentalItemsListView;

	@FXML
	private Label selectedRentalLabel;

	@FXML
	private ListView<Return> returnsListView;

	@FXML
	private ListView<RentalItem> returnItemsListView;

	@FXML
	private Label selectedReturnLabel;

	@FXML
	private Button logoutButton;

	@FXML
	private Label loggedInUserLabel;

	@FXML
	void logout(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource(Constants.LOGIN_FXML));

			loader.load();

			Parent parent = loader.getRoot();
			Scene scene = new Scene(parent);
			Stage newStage = new Stage();

			newStage.setScene(scene);
			newStage.initModality(Modality.APPLICATION_MODAL);

			newStage.show();
			Stage stage = (Stage) this.logoutButton.getScene().getWindow();

			stage.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets the logged in label of the view
	 *
	 * @param username the username to set
	 */
	public void setLoggedInLabel(String username) {
		super.loggedInUser = username;
		this.rentalDao = new RentalDao();
		this.currMember = MemberDao.getMemberByUsername(username);
		this.loggedInUserLabel.textProperty().set(username);
		this.populateRentalsListView(this.currMember.getMemberId());
	}

	private void populateRentalsListView(String memberId) {
		ArrayList<Rental> allRentalsForCustomer = this.rentalDao.getAllRentalsForMember(this.currMember.getMemberId());
		this.rentals = allRentalsForCustomer;
		this.rentalsListView.setItems(FXCollections.observableArrayList(this.rentals));
	}

	private void handleRentalListDoubleClick() {
		this.rentalsListView.setOnMouseClicked(event -> {
			if (event.getClickCount() == 1) {
				Rental selectedRental = this.rentalsListView.getSelectionModel().getSelectedItem();
				if (selectedRental != null) {
					ArrayList<RentalItem> rentalItems = this.rentalDao
							.getRentalItemsFromRental(selectedRental.getRentalId());
					ObservableList<RentalItem> rentalItemsObservable = FXCollections.observableArrayList(rentalItems);
					this.rentalItemsListView.setItems(rentalItemsObservable);
				}
			}
		});
	}

	@FXML
	void initialize() {
		assert this.rentalsListView != null
				: "fx:id=\"rentalsListView\" was not injected: check your FXML file 'MemberLandingPage.fxml'.";
		assert this.rentalItemsListView != null
				: "fx:id=\"rentalItemsListView\" was not injected: check your FXML file 'MemberLandingPage.fxml'.";
		assert this.selectedRentalLabel != null
				: "fx:id=\"selectedRentalLabel\" was not injected: check your FXML file 'MemberLandingPage.fxml'.";
		assert this.returnsListView != null
				: "fx:id=\"returnsListView\" was not injected: check your FXML file 'MemberLandingPage.fxml'.";
		assert this.returnItemsListView != null
				: "fx:id=\"returnItemsListView\" was not injected: check your FXML file 'MemberLandingPage.fxml'.";
		assert this.selectedReturnLabel != null
				: "fx:id=\"selectedReturnLabel\" was not injected: check your FXML file 'MemberLandingPage.fxml'.";
		assert this.logoutButton != null
				: "fx:id=\"logoutButton\" was not injected: check your FXML file 'MemberLandingPage.fxml'.";
		this.handleRentalListDoubleClick();
	}
}
