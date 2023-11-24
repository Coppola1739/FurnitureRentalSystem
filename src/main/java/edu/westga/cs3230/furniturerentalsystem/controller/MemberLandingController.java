package edu.westga.cs3230.furniturerentalsystem.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import edu.westga.cs3230.furniturerentalsystem.Main;
import edu.westga.cs3230.furniturerentalsystem.dao.MemberDao;
import edu.westga.cs3230.furniturerentalsystem.dao.RentalDao;
import edu.westga.cs3230.furniturerentalsystem.dao.ReturnDao;
import edu.westga.cs3230.furniturerentalsystem.model.Member;
import edu.westga.cs3230.furniturerentalsystem.model.Rental;
import edu.westga.cs3230.furniturerentalsystem.model.Return;
import edu.westga.cs3230.furniturerentalsystem.model.ReturnItem;
import edu.westga.cs3230.furniturerentalsystem.model.Transaction;
import edu.westga.cs3230.furniturerentalsystem.util.Constants;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
	private ListView<Return> returnsListView;

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
		this.populateReturnsListView(this.currMember.getMemberId());
	}

	private void populateRentalsListView(String memberId) {
		ArrayList<Rental> allRentalsForCustomer = this.rentalDao.getAllRentalsForMember(this.currMember.getMemberId());
		this.rentals = allRentalsForCustomer;
		this.rentalsListView.setItems(FXCollections.observableArrayList(this.rentals));
	}

	private void populateReturnsListView(String memberId) {
		ArrayList<Return> allReturnsForCustomer = ReturnDao.getAllReturnsForMember(this.currMember.getMemberId());
		this.returns = allReturnsForCustomer;
		this.returnsListView.setItems(FXCollections.observableArrayList(this.returns));
	}

	private void handleRentalListDoubleClick() {
		this.rentalsListView.setOnMouseClicked(event -> {
			if (event.getClickCount() == 1) {
				Rental selectedRental = this.rentalsListView.getSelectionModel().getSelectedItem();
				if (selectedRental != null) {
					Transaction tr = RentalDao.fetchRentalDetails(selectedRental.getRentalId());
					this.showReceipt(tr);
				}
			}
		});
	}
	
	private void showReceipt(Transaction tr) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Success");
		alert.setHeaderText("Rental Created");
		alert.setContentText("Here is your receipt:\n" + tr.generateReceipt());
		alert.showAndWait();
	}

	private void handleReturnListDoubleClick() {
		this.returnsListView.setOnMouseClicked(event -> {
			if (event.getClickCount() == 1) {
				Return selectedReturn = this.returnsListView.getSelectionModel().getSelectedItem();
				if (selectedReturn != null) {
					this.showReturnPopup(selectedReturn);
				}
			}
		});
	}

	private void showReturnPopup(Return selectedReturn) {
		Alert returnInformationPopup = new Alert(Alert.AlertType.INFORMATION);
		returnInformationPopup.setTitle(selectedReturn.getReturnId());
		returnInformationPopup.setHeaderText("Employee ID:" + selectedReturn.getEmployeeId() + " Member ID:"
				+ selectedReturn.getMemberId() + "\nReturn Number:" + selectedReturn.getReturnId());
		ArrayList<ReturnItem> itemsFromSelectedReturn = ReturnDao.getAllItemsInReturn(selectedReturn.getReturnId());
		returnInformationPopup.setContentText(Return.generateReturnReceipt(itemsFromSelectedReturn));

		returnInformationPopup.getButtonTypes().setAll(ButtonType.OK);

		returnInformationPopup.showAndWait();
	}

	@FXML
	void initialize() {
		assert this.rentalsListView != null
				: "fx:id=\"rentalsListView\" was not injected: check your FXML file 'MemberLandingPage.fxml'.";
		assert this.returnsListView != null
				: "fx:id=\"returnsListView\" was not injected: check your FXML file 'MemberLandingPage.fxml'.";
		assert this.logoutButton != null
				: "fx:id=\"logoutButton\" was not injected: check your FXML file 'MemberLandingPage.fxml'.";
		this.handleRentalListDoubleClick();
		this.handleReturnListDoubleClick();
	}
}
